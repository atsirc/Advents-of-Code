#!/bin/python3
from os.path import dirname, join
from operator import itemgetter
from math import ceil, floor

parentDir = dirname(__file__)
path = join(parentDir, 'input17.txt')
file = open(path, 'r')
input = file.readlines()
 
img = {}
clay = '▓'
sand = '|'
water = '~'
air = '.'

for line in input:
    line = line.lstrip()
    parts = line.split(', ')
    parts = sorted(parts)
    x, y = map(lambda i : i.split('=')[1],parts)
    line_in_x = '..' in x
    if line_in_x:
        start, end = map(int,x.split('..'))
    else:
        start, end = map(int,y.split('..'))

    for i in range(start, end + 1):
        if line_in_x:
            img[(i,int(y))] = clay
        else:
            img[(int(x),i)] = clay

minx = min(img.keys(), key=lambda c: c[0])[0]
maxx = max(img.keys(), key=lambda c: c[0])[0]
miny = min(img.keys(), key=lambda c: c[1])[1]
maxy = max(img.keys(), key=lambda c: c[1])[1]

moving = {}
moving[(500, miny)] = sand

def draw(array):
    for y in range(miny-2, maxy+1):
        string = ''
        for x in range(minx-1, maxx+2):
            string += array.get((x,y), air)
        print(string)
    print('\n\n')

def get_neighbours(pos, array):
    x,y = pos
    neighbours = [(x -1,y),(x,y+1),(x+1,y)]
    return [array.get(coord, air) for coord in neighbours]

def get_sides(pos, array):
    x,y = pos
    left_x = sorted([_x for _x in range(x, minx-1,-1) if array.get((_x,y), air) is clay], reverse=True)
    rigth_x = sorted([_x for _x in range(x, maxx+1) if array.get((_x,y), air) is clay])
    if not left_x or not rigth_x:
        return None, None
    return left_x[0], rigth_x[0]

def get_bounderies(pos, array):
    x,y = pos
    left_x, right_x = get_sides(pos,array)
    if not left_x:
        return None
    row_contents = []
    for col in range(left_x+1, right_x):
        row_contents.append(array.get((col,y+1), air))
    if all(content is clay or content is water for content in row_contents):
        row = y - 1
        while True:
            _left_x, _right_x = get_sides((x,row), array)
            if not _left_x:
                return left_x + 1, right_x - 1, row + 1
            if _left_x is not left_x or _right_x is not right_x:
                return left_x + 1, right_x -1, row + 1
            row -= 1
    return None

def fill_all(xmin, xmax, ymin, ymax, array):
    for y in range(ymin, ymax+1):
        for x in range(xmin, xmax+1):
            array[(x,y)] = water

def run(still, trickling):
    maxy = max(still.keys(), key=lambda c: c[1])[1]
    start_size = len(trickling)
    while True:
        turned_to_water = False
        copy = dict(trickling)
        
        for (x,y) in trickling:
            neighbours = get_neighbours((x,y), still)
            left,beneath,right = neighbours
            if beneath is air:
                if y+1 <= maxy:
                    copy[(x,y+1)] = sand
            elif beneath is clay or beneath is water:
                boundries = get_bounderies((x,y), still)
                if boundries:
                    fill_all(*boundries, y, still)
                    turned_to_water = True
                else:
                    if left is air:
                        copy[(x-1,y)] = sand
                    if right is air:
                        copy[(x+1,y)] = sand
            
        in_common = set(copy.keys()).intersection(still.keys())
        for key in in_common:
            del copy[key]
        trickling = copy
        if start_size == len(trickling) and not turned_to_water:
            break
        else:
            start_size = len(trickling)
    return {**still, **trickling}

img = run(img, moving)
draw(img)
print('Part 1: ', sum(content is water or content is sand for content in img.values()))
print('Part 2: ', sum(content is water for content in img.values()))
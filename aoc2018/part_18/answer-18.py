#!/bin/python
import os.path as os

file_name = 'input-18.txt'
dir = os.dirname(__file__)
path = os.join(dir, file_name)
file = open(path, 'r')
grid = file.read().splitlines()
_open, trees, lumber = '.', '|', '#'

area = {}
for y in range(len(grid)):
    for x in range(len(grid[y])):
        area[(x,y)] = grid[y][x]

rules = {
    _open: lambda _o, _t, _l: trees if _t >= 3 else _open,
    trees: lambda _o, _t, _l: lumber if _l >= 3 else trees,
    lumber: lambda _o, _t, _l: lumber if _t > 0 and _l > 0 else _open
}

def adjacent(area, point):
    x,y = point
    neigbours = []
    for _y in range(y-1, y+2):
        for _x in range(x-1, x+2):
            if (_x,_y) == point:
                continue
            neigbours.append(area.get((_x,_y), ''))
    return neigbours

def total_value(area):
    return list(area.values()).count(lumber) * list(area.values()).count(trees)

def time(minutes, area):
    for i in range(minutes):
        new_area = {}
        for point in area:
            neighbours = adjacent(area, point)
            __o,__t,__l = neighbours.count(_open), neighbours.count(trees), neighbours.count(lumber)
            new_area[point] = rules[area[point]](__o,__t,__l)
        area = new_area
    return total_value(area)

print(f'Part 1: {time(10, area)}')
#print(f'Part 2: {time(1000000000, area)}')

#Simple solution. A more programming style of solution would look for the repeat, but I did
#it myself by looking through the output.
#From at least minute 600 the values are the same
# minutes [600, 700, 800, 900, 1000, 1100, 1200]
array = [178825, 190491, 194400, 178097, 212176, 173545, 198830]

pointer = 0
for j in range(600, 1000000000, 100):
    pointer += 1

print(f'Part 2: {array[pointer % len(array)]}')
#! /usr/bin/python3
from os.path import dirname, join

test = 'test6.txt'
input = 'input6.txt'
dir = dirname(__file__)
path = join(dir, input)
file = open(path, 'r')
points = [dict(zip(['x', 'y'], map(int, line.split(',')))) for line in file.readlines()]
#points = sorted(points, key = lambda coord: (coord['x'], coord['y']))

minx = min(point['x'] for point in points)
maxx = max(point['x'] for point in points)
miny = min(point['y'] for point in points)
maxy = max(point['y'] for point in points)

keys = [f'{point["x"]},{point["y"]}' for point in points]
nearlings = {}

for x in range(minx, maxx):
    for y in range(miny, maxy):
        distances = [abs(point['x']-x)+abs(point['y']-y) for point in points]
        minDistance = min(distances)
        if distances.count(minDistance) == 1:
            minPos = keys[distances.index(minDistance)]
            nearlings.setdefault(minPos, []).append({'x':x, 'y':y})

for point in points:
    x,y = point.values()
    if minx == x or maxx == x:
        nearlings.pop(f'{x},{y}', None)
    elif miny == y or maxy == y:
        nearlings.pop(f'{x},{y}', None)

print (f'Part 1: {max(len(vals) for vals in nearlings.values())}')

region = []
for x in range(minx, maxx):
    for y in range(miny, maxy):
        distances = sum([abs(point['x']-x)+abs(point['y']-y) for point in points])
        if distances < 10000:
           region.append({'x':x, 'y':y})

print(f'Part 2: {len(region)}')
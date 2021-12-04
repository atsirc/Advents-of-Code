#!/bin/python3
from os.path import join, dirname
import re
fileName = 'input10.txt'
path = join(dirname(__file__), fileName)
file = open(path, 'r')
pattern = r'-?\d+'
keys = ['posX', 'posY', 'velX', 'velY']
points = [ dict(zip(keys, map(int, re.findall(pattern, line)))) for line in file.readlines()]
heaven = ' '
star = '▓'

def getBorders(points):
    return {
    'minX': min(point['posX'] for point in points),
    'minY': min(point['posY'] for point in points),
    'maxX': max(point['posX'] for point in points),
    'maxY': max(point['posY'] for point in points)
    }

def move(point):
    point['posX'] += point['velX']
    point['posY'] += point['velY']

def draw(points):
    borders = getBorders(points)
    pointList = {f'{point["posX"]},{point["posY"]}': star for point in points}
    converging = False
    if borders['maxX'] - borders['minX'] <= 70: #just a guess, but works
        converging = True
        for y in range(borders['minY'], borders['maxY'] + 1):
            str = ''
            for x in range(borders['minX'], borders['maxX'] + 1):
                str += pointList.get(f'{x},{y}', heaven)
            print(str)
    return converging

convergenceStarted = False
seconds = 0
while True:
    startState = convergenceStarted
    converging = draw(points)
    if converging == True:
        convergenceStarted = True
        print('seconds: ', seconds, '\n')
    if startState == True and converging == False:
        break
    for point in points:
        move(point)
    seconds += 1
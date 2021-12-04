#!/usr/bin/python3
import re

from os.path import dirname, join
fileName = './input3.txt'
currentDir = dirname(__file__)
path = join(currentDir, fileName)
file = open(path)
input = file.read().splitlines()

def getPoints(string):
    string = string.replace(' ', '')
    string = re.sub(r'#\d+@', '', string)
    parts = string.split(':')
    start = parts[0].split(',')
    size = parts[1].split('x')
    array = []

    for x in range(int(size[0])):
        x += int(start[0])
        for y in range(int(size[1])):
            y += int(start[1])
            array.append('%s,%s' % (x,y))
    
    return array

dict = {}
for line in input:
    array = getPoints(line)
    for point in array:
        dict[point] = dict.get(point, 0) + 1

print ('Part 1: ', sum(i > 1 for i in dict.values()))

for line in input:
    array = getPoints(line)
    claim = line.split('@')[0].rstrip()
    for point in array:
        if dict[point] != 1:
            break
        if point == array[len(array)-1]:
            print ('Part 2: ', claim)
            exit
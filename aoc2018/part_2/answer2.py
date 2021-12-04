#!/usr/bin/python3
from os.path import dirname, join
currentDir = dirname(__file__)
path = join(currentDir, './input2.txt')
file = open(path)
codes = file.read().splitlines()

length = len(codes[0])
twos = threes = 0

for val in codes:
    two = three = False
    for j in range(length):
        res = val.count(val[j])
        if res == 2:
            two = True
        elif res == 3:
            three = True
    if two:
        twos += 1
    if three:
        threes += 1

print ('Part 1: ', (twos * threes))

for i in range(length):
    for j, val in enumerate(codes):
        rest = codes[(j+1):]
        val1 = val[0:i] + val[(i+1):(length)]

        for val2 in rest:
            val2 = val2[0:i] + val2[(i+1):(length)]
            if val1 == val2:
                print ('Part 2: ', val1)
                exit
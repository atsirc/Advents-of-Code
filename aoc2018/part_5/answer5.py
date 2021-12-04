#!/usr/bin/python3

from os.path import dirname, join

fileName = './input5.txt'
dir = dirname(__file__)
path = join(dir, fileName)
file = open(path, 'r')
line = file.read().rstrip()

def retract(line):
    i = len(line) - 1
    length = len(line)

    while True:
        char1 = line[i]
        char2 = line[i-1]

        if char1 != char2 and char1.upper() == char2.upper():
            line = line[:i-1] + line[i+1:] 
        i -= 1
        if i == 0 or i == len(line):
            if len(line) == length:
                break
            i, length = len(line)-1, len(line)
    
    return line

copy = line
print (f'Part 1: {len(retract(copy))}')

allChars = list(set(line.lower()))
minLength = len(line)

for char in allChars:
    copy = line
    copy = copy.replace(char, '').replace(char.upper(), '')
    minLength = min(minLength, len(retract(copy)))

print (f'Part 2: {minLength}')
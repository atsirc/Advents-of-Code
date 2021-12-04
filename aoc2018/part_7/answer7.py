#! /usr/bin/python3

from os.path import join, dirname
import re

dir = dirname(__file__)
test = 'test7.txt'
input = 'input7.txt'
path = join(dir, input)
file = open(path, 'r')

pattern = r'[A-Z](?= )'
requirements = {}

for line in file.readlines():
    pair = re.findall(pattern, line)
    requirements.setdefault(pair[1], []).append(pair[0])

def findPath(requirements, available, found ):
    available.sort()
    char = available.pop(0)
    found.append(char)
    newAvailable = [k for k,v in requirements.items() if all(item in found for item in v) and k not in found]
    available.extend(newAvailable)
    available = list(set(available))
    return found if len(available) == 0 else findPath(requirements, available, found)

req = list(set(sum(requirements.values(), [])))
fulfilled = [item for item in req if item not in requirements.keys()]

string = findPath(requirements, fulfilled, [])

print("Part 1: ", "".join(string))
#print('FMOXCDGJRAUIHKNYZTESWLPBQV')

readyToProcess = [item for item in req if item not in requirements.keys()]
inProcess = {}
finished = []
workers = 5
i = 0

while True:
    for c, t in inProcess.items():
        inProcess[c] -= 1
        if inProcess[c] == 0:
            finished.append(c)
            newAvailable = [k for k,v in requirements.items() \
                if all(item in finished for item in v) and \
                k not in finished and k not in inProcess.keys()]
            readyToProcess.extend(newAvailable)

    [inProcess.pop(char, None) for char in finished]
    readyToProcess.sort()

    if len(inProcess) < workers:
        for j in range(workers - len(inProcess)):
            if len(readyToProcess) > 0:
                char = readyToProcess.pop(0)
                time = ord(char) - 4
                inProcess[char] = time
    
    if len(inProcess) == 0:
        break
    i += 1

print ("Part 2: ", i)
#!/bin/python3

"""
This answer only works for part 1 WITH MY INPUT, but it is pretty different from all the solutions I've seen
so I guess I could be somewhat proud (although after I checked it, it doesn't work with some other peoples input)
- mostly because I thought of giving up as soon as I read the full problem description...

The solution works by only keeping track of the lenghtiest solution. But clearly there is some bug in it though...
"""
from os.path import dirname, join

parentDir = dirname(__file__)
path = join(parentDir, 'test-3-20.txt')
file = open(path, 'r')
input = file.read().strip()

def set_parentheses(text):
    i = 1
    parentheses_beginnings = list()
    while text[i] != '$':
        if text[i] == '(':
            parentheses_beginnings.append(i)
        elif text[i] == ')':
            last_added = parentheses_beginnings.pop()
            parentheses[last_added] = i
        i += 1

def find_parts(text, index):
    i = 0
    pipes = [0]
    while True:
        partition = text.find('|', i)
        other = text.find('(', i)
        while other != -1 and other < partition:
            end = parentheses[index + other] - index
            partition = text.find('|', end)
            other = text.find('(', end)
        if partition == -1:
            break
        pipes.append(partition)
        i += partition - pipes[-2] + 1

    parts = []
    for j in range(len(pipes)):
        if j == len(pipes) -1:
            parts.append(text[pipes[j]: len(text)].strip('|'))
        else:
            parts.append(text[pipes[j]: pipes[j+1]].strip('|'))
    return parts


def find_route(text, index=0, length=0):
    i = 0
    while i < len(text):
        if text[i] == '(':
            end_index = parentheses[index]
            index += 1
            i += 1
            parts = find_parts(input[index:end_index], index)
            lengths = []
            if '' in parts:
                #+1 because one +1 was already performed when getting the parts
                jump = sum([len(part) for part in parts]) + (len(parts) - 1) + 1
                index += jump
                i += jump
                continue
            else:
                for part in parts:
                    new_length = find_route(part, index, length)
                    index += len(part) + 1
                    i += len(part) + 1
                    lengths.append(new_length)
                length = max(lengths)
                continue
        elif text[i] in ['N', 'S', 'W', 'E']:
            length += 1
        i += 1
        index += 1

    return length

parentheses = dict()
set_parentheses(input)
part1 = find_route(input)
print(part1)
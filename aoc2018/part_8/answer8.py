#!/bin/python3
from os.path import dirname, join

test = 'test8.txt'
input = 'input8.txt'
dir = dirname(__file__)
path = join(dir, input)
string = open(path, 'r').read().split(' ')

def setUp(pos, string):
    node = {}
    children = int(string[pos])
    metas = int(string[pos+1])
    pos += 2

    if children > 0:
        for i in range(children):
            newNode, pos = setUp(pos, string)
            node.setdefault('children',[]).append(newNode)
    for meta in range(metas):
        val = int(string[pos + meta])
        node.setdefault('metas',[]).append(val)
    pos += metas
    return node, pos

def countMetas(node, sum):
    if 'children' in node.keys():
        for child in node['children']:
            sum = countMetas(child, sum)
    for meta in node['metas']:
        sum += meta
    return sum

def findValues(node, sum): 
    if 'children' not in node.keys():
        for meta in node['metas']:
            sum += meta
    else:
        children = node['children']
        for childNo in node['metas']:
            if childNo <= len(children):
                sum = findValues(children[childNo-1], sum)
    return sum

node, pos = setUp(0, string)

print("Part 1:", countMetas(node, 0))
print('Part 2:', findValues(node, 0))
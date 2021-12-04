#!/bin/python3
"""
#betavuoros

from collections import defaultdict
from os.path import dirname, join

parentDir = dirname(__file__)
path = join(parentDir, 'test-3-20.txt')
file = open(path, 'r')
text = file.read().strip()

match = dict() # map index of '(' to index of matching ')'
left_of = dict() # map index of '|' to index of '(' containing it
paren_stack = [] # stack of indices of unclosed '('
bars = defaultdict(list) # map index of '(' to indices of all '|' in its group
for i, c in enumerate(text):
    if c == '$':
        assert not paren_stack
        break
    elif c == '(':
        paren_stack.append(i)
    elif c == '|':
        left_of[i] = paren_stack[-1]
        bars[paren_stack[-1]].append(i)
    elif c == ')':
        other = paren_stack.pop()
        match[other] = i

directions = {
    "N": (-1,0),
    "E": (0,1),
    "S": (1,0),
    "W": (0,-1),
}

def add_dir(point, d):
    r, c = point
    dr, dc = directions[d]
    return (r + dr, c + dc)

graph = defaultdict(set)
def connect(p1, p2):
    graph[p1].add(p2)
    graph[p2].add(p1)

# build the graph

explored = set()

def explore(i, point):
    # follow the path starting from position `i` in the regex and location
    # `point` in the map
    while True:
        if (i, point) in explored:
            # don't explore the same point at the same index more than once
            return
        explored.add((i, point))
        if text[i] == '$':
            break
        elif text[i] in "NESW":
            next_point = add_dir(point, text[i])
            connect(point, next_point)
            i += 1
            point = next_point
        elif text[i] == '|':
            # we finished a branch; jump to the ')'
            i = match[left_of[i]] + 1
        elif text[i] == ')':
            i += 1
        elif text[i] == '(':
            # recursively explore branches other than the first by starting at
            # the character after each '|'
            for bar in bars[i]:
                explore(bar+1, point)
            # continue exploring the first branch without recursing
            i += 1

explore(1, (0,0))

# perform breadth-first search
q = [(0,0)]
seen = set(q)
dist = 0
ans = 0
while q:
    if dist >= 1000:
        ans += len(q)
    qq = []
    for point in q:
        for p2 in graph[point]:
            if p2 not in seen:
                qq.append(p2)
                seen.add(p2)
    q = qq
    dist += 1

print(dist-1)
print(ans)
"""
"""

#https://www.reddit.com/r/adventofcode/comments/a7uk3f/2018_day_20_solutions/ sciyoshi

import networkx

maze = networkx.Graph()

paths = open('test-3-20.txt').read()[1:-1]

pos = {0}  # the current positions that we're building on
stack = []  # a stack keeping track of (starts, ends) for groups
starts, ends = {0}, set()  # current possible starting and ending positions

for c in paths:
    if c == '|':
        # an alternate: update possible ending points, and restart the group
        ends.update(pos)
        pos = starts
    elif c in 'NESW':
        # move in a given direction: add all edges and update our current positions
        direction = {'N': 1, 'E': 1j, 'S': -1, 'W': -1j}[c]
        maze.add_edges_from((p, p + direction) for p in pos)
        pos = {p + direction for p in pos}
    elif c == '(':
        # start of group: add current positions as start of a new group
        stack.append((starts, ends))
        starts, ends = pos, set()
    elif c == ')':
        # end of group: finish current group, add current positions as possible ends
        pos.update(ends)
        starts, ends = stack.pop()

# find the shortest path lengths from the starting room to all other rooms
lengths = networkx.algorithms.shortest_path_length(maze, 0)

print('part1:', max(lengths.values()))
print('part2:', sum(1 for length in lengths.values() if length >= 1000))
"""

#https://github.com/mrFred489/AoC2018/blob/master/20.py

from collections import *
import itertools
import sys
import re

f = open("test-20.txt").read().strip("\n")

d = {
    "N": (0, -1),
    "E": (1, 0),
    "S": (0, 1),
    "W": (-1, 0)
}

positions = []
prev_x, prev_y = x, y = 0, 0
distances = defaultdict(int)
for c in f[1:-1]:
    if c == "(":
        positions.append((x, y))
    elif c == ")":
        x, y = positions.pop()
    elif c == "|":
        x, y = positions[-1]
    else:
        dx, dy = d[c]
        x += dx
        y += dy
        if distances[(x, y)] != 0:
            distances[(x, y)] = min(distances[(x, y)], distances[(prev_x, prev_y)]+1)
        else:
            distances[(x, y)] = distances[(prev_x, prev_y)]+1
    prev_x, prev_y = x, y

print(max(distances.values()))
print(len([x for x in distances.values() if x >= 1000]))
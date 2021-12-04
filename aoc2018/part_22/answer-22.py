#!/bin/python
import copy
from operator import attrgetter

target = (11,722)
depth = 10689
types = rocky, wet, narrow = '^', '~', '|'
tools = {
    rocky: ['climbing gear', 'torch'],
    wet: ['climbing gear', 'neither'],
    narrow: ['torch', 'neither']
}
switch = 7

class Region:

    def __init__(self, er_lvl, risk):
        self.er_lvl = er_lvl
        self.type = risk
    
    def get_er_lvl(self):
        return self.er_lvl
    
    def get_risk(self):
        return self.type

    def get_type(self):
        return types[self.type]
    
    def __str__(self):
        return self.get_type()

class Path:

    def __init__(self, point, tool):
        self.point = point
        self.tool = tool
        self.minutes = 0

    def switch_tool(self, new_tool):
        if new_tool != self.tool:
            self.tool = new_tool
            self.minutes += 7

    def move(self, new_point):
        self.point = new_point
        self.minutes += 1

    def get_time(self):
        return self.minutes

    def __str__(self):
        return str(self.minutes)

def generate_map(target, depth ):
    _map = {}
    for y in range(0,target[1] + 1):
        for x in range(0,target[0]+1):
            if (x,y) in [(0,0), target]:
                geo_indx = 0
            elif y == 0:
                geo_indx = x * 16807
            elif x == 0:
                geo_indx = y * 48271
            else:
                geo_indx = _map[(x-1,y)].get_er_lvl() * _map[(x,y-1)].get_er_lvl()
            erosion_lvl = (geo_indx + depth) % 20183
            risk = erosion_lvl % 3
            _map[(x,y)] = Region(erosion_lvl, risk)
    return _map

def get_neighbours(point):
    x,y = point
    return (x-1,y), (x,y-1), (x+1,y), (x,y+1)

def split(_map_, path, new_point):
    copy1 = copy.deepcopy(path)
    copy2 = copy.deepcopy(path)
    possible_tools = tools[_map_[new_point].get_type()] if new_point != target else ['torch', 'torch']
    copy1.switch_tool(possible_tools[0])
    copy2.switch_tool(possible_tools[1])
    copy1.move(new_point)
    copy2.move(new_point)
    return [copy1, copy2]

def find_path(_map_, start):
    all_paths = {(*start.point, start.tool): start}
    added_paths = [(0,0, 'torch')]
    visited = [(0,0)]
    finished = None

    while added_paths:
        new_paths = []
        for path in added_paths:
            if finished:
                if all_paths[path].minutes >= finished.minutes:
                    continue
            new_points = get_neighbours((path[0:2]))
            for point in new_points:
                if _map_.get(point, None) and point not in visited:
                    paths = split(_map_, all_paths[path], point)
                    if point == target:
                        if finished:
                            paths.append(finished)
                        finished = min(paths, key=attrgetter('minutes'))
                    else:
                        path1, path2 = paths
                        key1, key2 = (*path1.point, path1.tool), (*path2.point, path2.tool)
                        current1 = all_paths.get(key1, None)
                        current2 = all_paths.get(key2, None)
                        all_paths[key1] = current1 if current1 and current1.minutes < path1.minutes else path1
                        all_paths[key2] = current2 if current2 and current2.minutes < path2.minutes else path2
                        new_paths.append(key1)
                        new_paths.append(key2)

        visited = list(set([path.point for path in all_paths.values()]))
        added_paths = list(set(new_paths))
    
    return finished


_map_ = generate_map(target, depth)
print (sum([region.get_risk() for region in _map_.values()]))

_map2_ = generate_map((target[0]+50, target[1]+50), depth)
start = Path((0,0), 'torch')
paths = find_path(_map2_, start)

print(paths)


"""
for y in range(target[1]+1):
    string = ''
    for x in range(target[0]+1):
        string += str(_map_[(x,y)])
    print (string)
"""
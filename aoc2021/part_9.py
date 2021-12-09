f = open('input_day_9.txt')
lines = [line for line in f.read().splitlines()]
w,h = len(lines[0]), len(lines)
    
#  creating grid 
grid = dict()
for y in range(h):
    line = lines[y]
    for x in range(w):
        grid[(x+1,y+1)] = int(line[x])

def get_smallest_top(xy, top, smallest_tops, path):
    path.append(xy)
    x,y = xy
    adjacent = dict()
    adjacent[(x,y-1)] = grid.get((x,y-1), None)
    adjacent[(x,y+1)] = grid.get((x,y+1), None)
    adjacent[(x-1,y)] = grid.get((x-1,y), None)
    adjacent[(x+1,y)] = grid.get((x+1,y), None)
    adjacent = {k:v for k,v in adjacent.items() if v != None}
    smallest = min(adjacent.values())
    if smallest > top:
         smallest_tops[xy] = smallest_tops.get(xy, []) + path
         return 
    for xy_, top_ in adjacent.items():
        if top_ < top:
            return get_smallest_top(xy_, top_, smallest_tops, path)


#   Part 1 getting paths to tops with top xy as key
def get_smallest_tops():
    st = dict()
    for xy, top in grid.items():
        if xy not in st:
            get_smallest_top(xy, top, st, [])
    return st 

#    Part 2 getting lengths of paths to tops
def get_path_lengths(smallest_tops):
    sets = { k: set(v) for k, v in smallest_tops.items()}
    lengths = dict() 
    for end, paths in sets.items():
        length = 0
        for path in paths:
            if grid[path] != 9:
                length += 1
        lengths[end] = length
    sorted_lengths = sorted(lengths.values())
    return sorted_lengths

smallest_tops = get_smallest_tops()
path_lengths = get_path_lengths(smallest_tops)
print(f'Part 1: {sum([grid[key]+1 for key in smallest_tops.keys()])}')
print(f'Part 2: {path_lengths[-1]*path_lengths[-2]*path_lengths[-3]}')


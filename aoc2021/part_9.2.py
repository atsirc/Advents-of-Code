f = open('input_day_9.txt')
lines = [line for line in f.read().splitlines()]
w,h = len(lines[0]), len(lines)
    
#  creating grid 
grid = dict()
for y in range(h):
    line = lines[y]
    for x in range(w):
        grid[(x+1,y+1)] = int(line[x])

#   maybe a helper function? anyways recurses through the grid
def get_smallest_top(xy, top, path, memo):
    if top != 9: path.append(xy)
    x,y = xy
    adj = dict()
    adj[(x,y-1)] = grid.get((x,y-1), None)
    adj[(x,y+1)] = grid.get((x,y+1), None)
    adj[(x-1,y)] = grid.get((x-1,y), None)
    adj[(x+1,y)] = grid.get((x+1,y), None)
    adj = {k:v for k,v in adj.items() if v != None}
    smallest = min(adj.values())
    if smallest > top:
        memo[xy] = list(set(memo.get(xy, []) + path))
        return 
    else:
        for xy_, top_ in adj.items():
            if top_ < top:
                return get_smallest_top(xy_, top_, path, memo)

#  getting paths to tops with top xy as key
def get_smallest_tops():
    memo = dict()
    for xy, top in grid.items():
         get_smallest_top(xy, top, [], memo)
    return memo 

#    Part 2 getting lengths of paths to tops
def get_path_lengths(smallest_tops):
    lengths = [len(path) for path in smallest_tops.values()]
    return sorted(lengths) 

smallest_tops = get_smallest_tops()
path_lengths = get_path_lengths(smallest_tops)
print(f'Part 1: {sum([grid[key]+1 for key in smallest_tops.keys()])}')
print(f'Part 2: {path_lengths[-1]*path_lengths[-2]*path_lengths[-3]}')

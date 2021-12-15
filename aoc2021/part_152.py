f = open('input_day_15.txt')
lines = f.read().splitlines()
grid = dict()
w = len(lines[0])
h = len(lines)

for y in range(h):
    line = lines[y]
    for x in range(w):
        grid[(x,y)] = int(line[x])

grid2 = dict()
for j in range(0,5):
    extra = 0
    for i in range(0,5):
        for k in range(h):
            for l in range(w):
                res = grid[(l,k)] 
                new = res + i + j + extra 
                grid2[(w*i+l,k+j*h)] = 9 if new % 9 == 0 else new % 9
    extra += 1

def dijkstra(grid):
    unvisited = {node: None for node in grid.keys()}
    visited = {}
    current = (0,0)
    currentDistance = 0
    unvisited[current] = currentDistance
    while True:
        x,y = current 
        possiblities = [(x, y+1), (x+1, y), (x-1, y), (x, y-1)]
        for point in possiblities:
            if point not in unvisited:
                continue
            newDistance = currentDistance + grid[point] 
            if unvisited[point] is None or unvisited[point] > newDistance:
                unvisited[point] = newDistance
        visited[current] = currentDistance
        del unvisited[current]
        if not unvisited:
            break
        candidates = [node for node in unvisited.items() if node[1]]
        current, currentDistance = sorted(candidates, key = lambda x: x[1])[0]
    return visited

# for j in range(h*5):
#     string = ''
#     for i in range(w*5):
#        striing += str(grid2[(i,j)] )
#     print(string)

paths = dijkstra(grid2)
print(f'Part 2: {paths[(w*5-1,h*5-1)]}')

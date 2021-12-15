from collections import defaultdict
f = open('input_day_15.txt')
lines = f.read().splitlines()
grid = dict()
w = len(lines[0])
h = len(lines)
end = w-1, h-1

for y in range(h):
    line = lines[y]
    for x in range(w):
        grid[(x,y)] = int(line[x])

shoretest_paths = defaultdict(lambda: None)
least_points = sum(grid.values())

def move(point, points):
    global least_points
    if points < least_points:
        if point == end:
            least_points = points
        elif shoretest_paths[point] == None or points < shoretest_paths[point]:
            shoretest_paths[point] = points
            x,y = point
            possiblities = [(x, y+1), (x+1, y), (x-1, y), (x, y-1)]
            for point in possiblities:
                if point[0] < 0 or point[1] < 0 or point[0] > end[0] or point[1] > end[1]:
                    continue
                points_ = points + grid[point]
                if points_ < least_points: 
                    move(point, points_)

move((0,0), 0)
print(f'Part 1: {least_points}')

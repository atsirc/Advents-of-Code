from collections import defaultdict
f = open('input_day_11.txt')
lines = [line for line in f.read().splitlines()]
w, h = len(lines[0]), len(lines)
grid = defaultdict(lambda: None)

for j in range(len(lines)):
    line = lines[j]
    for i in range(len(line)):
        grid[(i,j)] = int(lines[j][i])

def set_adjacent(xy, turn):
    x, y = xy
    loop = [-1, 0, 1]
    for j in loop:
        for i in loop:
            a_x = x + i
            a_y = y + j
            axy = (a_x, a_y)
            if grid[axy] != None and axy not in turn:
                grid[axy] = (grid[axy] + 1) % 10
                if grid[axy] == 9:
                    turn.append(axy)

def loop(days):
    flashed = dict()
    for day in range(1,days):
        zeros = True 
        flashed[day] = []
        for col in range(h):
            for row in range(w):
                xy = (row, col)
                grid[xy] = (grid[xy] + 1) % 10
                if grid[xy] == 9:
                    flashed[day].append(xy)
                if grid[xy] != 0:
                    zeros = False
        for el in flashed[day]:
            set_adjacent(el, flashed[day])
        if zeros:
            print(f'Part 2: {day}')
            break
    print(f'Part 1: {sum([len(arr) for day, arr in flashed.items() if day < 100])}')

loop(1000)




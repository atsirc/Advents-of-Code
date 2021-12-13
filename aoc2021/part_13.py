f = open('input_day_13.txt')
coords, folds = f.read().split('\n\n')
grid = dict()

for point in coords.split('\n'):
    x,y = point.split(',')
    grid[(int(x),int(y))] = True

folds = [line.replace('fold along ', '') for line in folds.split('\n') if len(line) > 0]

def fold(instruction):
    direction, f_line = instruction.split('=')
    changed_points = dict()
    f_line = int(f_line)
    for (x,y), point in grid.items():
        if point:
            if direction == 'x':
                if x > f_line: 
                    change = 2 * (f_line - x)
                    x_, y_ = (x + change), y
                    changed_points[(x_,y_)] = True
                    changed_points[(x,y)] = False 
            else:
                if y > f_line:
                    change = 2 * (f_line - y)
                    x_, y_ = x, (y + change)
                    changed_points[(x_,y_)] = True
                    changed_points[(x,y)] = False 
    return grid | changed_points

#Part 1
grid = fold(folds.pop(0))
print(f'Part 1: {sum([1 for k, v in grid.items() if v ])}')

#Part 2
for f in folds:
    grid = fold(f)

maxx = max([x for (x,y), val in grid.items() if val])
maxy = max([y for (x,y), val in grid.items() if val])
minx = min([x for (x,y), val in grid.items() if val])
miny = min([y for (x,y), val in grid.items() if val])

print('Part 2:\n')
for y in range(miny, maxy+1):
    string = ''
    for x in range(minx, maxx+1):
       string += '▓' if grid.get((x,y), False) else ' '
    print(string)

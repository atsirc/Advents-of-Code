f = open('input_day_20.txt')
code, grid = f.read().split('\n\n')
grid = grid.splitlines()
w = (0, len(grid[0]))
h = (0, len(grid))
image = dict()
iter = 0

for y in range(h[1]):
    for x in range(w[1]):
        image[(x,y)] = grid[y][x]

def get_number(xy):
    x, y = xy
    val = ''
    for k in range(-1, 2):
        for l in range(-1,2):
            p_y, p_x = y+k, x+l
            """
            Reminder to myself: this was the part i didn't understand. The test input algoritm started with . 
            therefore all added padding was . The actual input started with # which creates an either or situation
            Every other iteration the padding is . and then #

            """
            if code[0] == '#':
                val += image[(p_x,p_y)] if (p_x, p_y) in image else '#' if iter%2 == 0 else '.'
            else:
                val += image[(p_x,p_y)] if (p_x, p_y) in image else '.' 
    val = val.replace('.', '0') 
    val = val.replace('#', '1')
    return code[int(val,2)]

def enhance(grid):
    global w,h,iter
    iter += 1
    h = hs, he = h[0]-1, h[1]+1 
    w = ws, we = w[0]-1, w[1]+1
    newgrid = dict() 
    for y in range(hs, he+1):
        for x in range(ws, we+1):
            newgrid[(x,y)] = get_number((x,y))
    return newgrid

# def get_image():
#     maxx = w[1]
#     minx = w[0]
#     miny = h[0]
#     maxy = h[1]
    
#     for y in range(miny, maxy+1):
#         string = ''
#         for x in range(minx, maxx+1):
#            string += image[(x,y)]
#         print(string)

for i in range(0,2):
    image = enhance(image)

# hard coded limit values because the rest of the answer is so ugly, so why not...
print(sum([1 for k,v in image.items() if v == '#' and -2 <= k[0] <= 102 and -2 <= k[1] <= 102]))

for i in range(0,48):
    image = enhance(image)

print(sum([1 for k,v in image.items() if v == '#' and -50 <= k[0] <= 150 and -50 <= k[1] <= 150]))

#!/bin/python3
from operator import attrgetter
from os.path import dirname, join
fileName = 'input13.txt'
path = join(dirname(__file__), fileName)
file = open(path, 'r')
lines = file.read().split('\n')

class Cart:
    turns = 0
    intersection_directions = [-1 , 0, 1]
    directions = ['L','U','R','D']
    velocity = {'L': {'x': -1, 'y':0}, 'R': {'x': 1, 'y':0}, 'U': {'x': 0, 'y':-1}, 'D': {'x': 0, 'y':1}}
    crashed = False
    def __init__(self,x,y, direction):
        self.x = x
        self.y = y
        self.set_direction(direction)

    def set_direction(self, new_direction):
        self.direction = self.directions.index(new_direction)

    def get_direction(self):
        return self.directions[self.direction]
    
    def move(self):
        self.x += self.velocity[self.get_direction()]['x']
        self.y += self.velocity[self.get_direction()]['y']
    
    def turn(self):
        turn_direction = self.intersection_directions[self.turns%3]
        self.direction = ( len(self.directions) + self.direction + turn_direction) % len(self.directions)
        self.turns += 1

    def crash(self):
        self.crashed = True
    
    def __str__(self):
        return f'{self.x},{self.y}'

def setup(lines):
    _corners = {'/': {'U': 'R', 'R': 'U','L': 'D', 'D': 'L'}, '\\': {'R': 'D','U': 'L', 'D': 'R', 'L': 'U'}} 
    _intersection = '+'
    _carts = {'<': 'L', '>': 'R','^': 'U','v': 'D'}
    intersections = []
    corners = {}
    carts = []
    for y in range(len(lines)):
        for x in range(len(lines[y])):
            if lines[y][x] == _intersection:
                intersections.append(f'{x},{y}')
            elif lines[y][x] in _corners:
                corners[f'{x},{y}'] = _corners[lines[y][x]]
            elif lines[y][x] in _carts:
                carts.append(Cart(x,y,_carts[lines[y][x]]))
    return carts, intersections, corners

def run(carts, intersections, corners, look_for_first_crash=False):
    carts_coords = [str(cart) for cart in carts]
    while True:
        carts.sort(key=attrgetter('x','y'))
        for cart in carts:
            if cart.crashed == False:
                carts_coords.remove(str(cart))
                cart.move()
                
                if corners.get(str(cart), None):
                    current_direction = cart.get_direction()
                    new_direction = corners[str(cart)][current_direction]
                    cart.set_direction(new_direction)
                elif str(cart) in intersections:
                    cart.turn()

                if str(cart) not in carts_coords:
                    carts_coords.append(str(cart))
                    if len(carts_coords) == 1:
                        return str(cart)
                elif look_for_first_crash:
                    return str(cart)
                else:
                    other_cart = next(o_cart for o_cart in carts if o_cart != cart and str(o_cart) == str(cart))
                    other_cart.crash()
                    cart.crash()
                    carts_coords.remove(str(cart))

part1 = run(*setup(lines), True)
print("Part 1: ",  part1)

part2 = run(*setup(lines))
print('Part 2: ', part2)
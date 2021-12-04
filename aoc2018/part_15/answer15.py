#!/bin/python3

"""
this solution would not have been possible for me without using Thomas Redferns solution as a guide
https://github.com/tredfern0/adventofcode2018/blob/master/15.ipynb
his solution helped me code the find_all_routes (more or less copy paste)
and understand that one should choose the route which endpoint is lowest in reading order 
in addition to that which is explained in the problem description
"""

from os.path import dirname, join
from operator import itemgetter, attrgetter

parentDir = dirname(__file__)
path = join(parentDir, './input_15.txt')
file = open(path, 'r')
input = file.readlines()

class Creature:
    hp = 200
    is_alive = True

    def __init__(self, name, pos, attack_power=3):
        self.set_pos(pos)
        self.attack_power = attack_power
        self.name = 'Goblin' if name == 'G' else 'Elf'
    
    def attack(self,other):
        other.hp -= self.attack_power
    
    def set_pos(self, pos):
        self.pos = pos
        x,y = map(int, pos.split(','))
        self.x, self.y = x, y
    
    def get_pos(self):
        return self.pos
    
    def get_pos_tuple(self):
        return (self.x, self.y)

    def __str__(self):
        return self.name[0]

def setup(input):
    board = {}
    beigns = []
    width = height = 0
    for y in range(len(input)): 
        height = y
        for x in range(len(input[y])):
            width = x
            square = input[y][x]
            if square  == 'G' or square == 'E':
                content = Creature(square, f'{x},{y}')
                beigns.append(content)
            else:
                content = square
            board[f'{x},{y}'] = content
    return beigns, board, width, height

def find_all_routes(start, enemy_adjacent, board):
    moves = []
    adjacent = get_empty(start, board)
    
    for move in adjacent:
        i = 1
        if move in enemy_adjacent:
            moves.append({'tile': move, 'steps': i, 'end': move})
            continue
        seen = [start, move]
        next_points = [point for point in get_empty(move, board) if point not in seen]
        run = True
        while run:
            i+=1
            new_next_points = []

            for point in next_points:
                if point in seen:
                    continue
                seen.append(point)
                if point in enemy_adjacent:
                    moves.append({'tile': move, 'steps': i, 'end': point})
                    run = False
                    continue
                new_next_points.extend([point_ for point_ in get_empty(point, board) if point_ not in seen])
            
            next_points = list(set(new_next_points))
            if not next_points:
                run = False
    return moves

def to_attack(beign, enemies_within_reach, result):
    enemies = sorted(enemies_within_reach, key=attrgetter('hp','y','x'))
    enemy = enemies[0]
    beign.attack(enemy)
    if enemy.hp <= 0:
        enemy.is_alive = False
        result['killed'] = enemy
    return result

def tuple_as_string(pos):
    return f'{pos[0]},{pos[1]}'

def turn(beign, enemies, board):
    result = {'move': None, 'killed': None}
    own_adjacent = get_adjacent(beign.get_pos_tuple())
    enemy_positions = [enemy.get_pos_tuple() for enemy in enemies]
    adjacent_enemies_pos = list(set(own_adjacent).intersection(enemy_positions))
    adjacent_enemies = [enemy for enemy in enemies if enemy.get_pos_tuple() in adjacent_enemies_pos]
    if adjacent_enemies:
        result = to_attack(beign, adjacent_enemies, result)
    else:
        in_range = [position for enemy_position in enemy_positions for position in get_empty(enemy_position, board)]
        in_range = list(set(in_range))
        all_routes = find_all_routes(beign.get_pos_tuple(), in_range, board)
        if not all_routes:
            return result
        shortest = min([route['steps'] for route in all_routes])
        shortest_routes = [route for route in all_routes if route['steps'] == shortest]
        shortest_routes = sorted(shortest_routes, key=lambda val: (val['tile'][1], val['tile'][0]))
        shortest_routes = sorted(shortest_routes, key=lambda val: (val['end'][1], val['end'][0]))
        pos = tuple_as_string(shortest_routes[0]['tile'])
        beign.set_pos(pos)
        result['move'] = pos
        new_own_adjacent = get_adjacent(beign.get_pos_tuple())
        adjacent_enemies_pos = list(set(new_own_adjacent).intersection(enemy_positions))
        adjacent_enemies = [enemy for enemy in enemies if enemy.get_pos_tuple() in adjacent_enemies_pos]
        if adjacent_enemies:
            result = to_attack(beign, adjacent_enemies, result)
    return result

def get_adjacent(point):
    x,y = point
    return [(x+1,y),(x-1,y),(x, y+1),(x,y-1)]

def get_empty(point, board):
    return [(x,y) for x,y in get_adjacent(point) if board[f'{x},{y}'] == '.']

def one_round(beigns, board):
    for beign in beigns:
        if beign.is_alive:
            start_pos = beign.get_pos()
            enemy_name = 'Goblin' if beign.name == 'Elf' else 'Elf'
            enemies = [creature for creature in beigns if creature.name == enemy_name and creature.is_alive]
            if not enemies:
                return True, beigns, board
            move, kill = turn(beign, enemies, board).values()
            if move:
                board[start_pos] = '.'
                board[move] = str(beign)
            if kill:
                board[kill.get_pos()] = '.'
    return False, beigns, board

def run(beigns, board):
    turns = 0
    end = False
    while True:
        beigns = sorted(beigns, key=attrgetter('y', 'x'))
        end, beigns, board = one_round(beigns, board)
        if end:
            sum_result = turns* sum(beign.hp for beign in beigns if beign.is_alive)
            return sum_result, beigns, board
        turns += 1

beigns,board, width, height = setup(input)
score, beigns, board = run(beigns, board)
print('Part 1: ', score)

i = 5
while True:
    _beigns = []
    _board = {}
    elves = 0
    width = height = 0
    for y in range(len(input)): 
        height = y
        for x in range(len(input[y])):
            width = x
            square = input[y][x]
            if square  == 'G' or square == 'E':
                attack_points = 3 + i if square == 'E' else 3
                content = Creature(square, f'{x},{y}', attack_points)
                _beigns.append(content)
            else:
                content = square
            _board[f'{x},{y}'] = content

    elves = sum(_beign.name == 'Elf' for _beign in _beigns)
    score, _beigns, _board = run(_beigns,_board)
    elves_left = sum(_beign.name == 'Elf' for _beign in _beigns if _beign.is_alive)
    if elves_left == elves:
        print('Part 2: ', score)
        break
    i += 1
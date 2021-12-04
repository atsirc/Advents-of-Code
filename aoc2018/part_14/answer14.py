#!/bin/python3

stop = 157901
elves_pos = [0,1]
recipes = '37'

while len(recipes) < stop + 10:
    recipes += str(int(recipes[elves_pos[0]]) + int(recipes[elves_pos[1]]))
    elves_pos = [(elf + int(recipes[elf]) + 1) % len(recipes) for elf in elves_pos]

print('Part 1: ', recipes[-10:])

find = str(stop)
if find not in recipes:
    while find not in recipes[-(len(find) +1):]:
        recipes += str(int(recipes[elves_pos[0]]) + int(recipes[elves_pos[1]]))
        elves_pos = [(elf + int(recipes[elf]) + 1) % len(recipes) for elf in elves_pos]

print('Part 2: ', recipes.index(find))
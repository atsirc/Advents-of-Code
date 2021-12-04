#!/bin/python3
stop = 2018

elves_pos = [0,1]
recipes = [3,7]
biggest_index = 0
while len(recipes) < stop + 10:
    new_recipe = str(recipes[elves_pos[0]] + recipes[elves_pos[1]])
    new_recipe = [int(digit) for digit in new_recipe]
    recipes.extend(new_recipe) 
    elves_pos = [(elf + recipes[elf] + 1) % len(recipes) for elf in elves_pos]
    biggest_index = max(*elves_pos, biggest_index)


print("".join(map(str, recipes[-10:])))
print(biggest_index)
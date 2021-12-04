#!/bin/python3
from os.path import dirname, join
fileName = 'input12.txt'
path = join(dirname(__file__), fileName)
file = open(path, 'r')
lines = file.readlines()

flower, empty = '#', '.'
pots = '....' + lines[0].strip('inital state: ').rstrip() + '....'
rules = dict(line.rstrip().split(' => ') for line in lines[2:])
keys = range(-4, len(pots))
pots = dict(zip(keys,pots))

def get_new_generation(current):
    new_gen = ''
    string = ''.join(current.values())
    for index in range(len(string)):
        slice = string[index-2:index+3]
        if slice in rules.keys():
            new_gen += rules.get(slice)
        else:
            new_gen += empty
    new_gen = new_gen.center(len(current) + 2, empty)
    new_keys = range(list(current.keys())[0]-1, len(string) + 1)
    return dict(zip(new_keys,new_gen))

prev_generation = pots
for i in range(1,105):
    prev_generation = new_generation = get_new_generation(prev_generation)
    if i == 20:
        print("Part 1: ", sum(k for k,v in new_generation.items() if v == flower))
    """
    if i > 99:
        all = sum(k for k,v in new_generation.items() if v == flower)
        firsti = next(k for k,v in new_generation.items() if v == flower)
        string = ''.join(list(new_generation.values())).strip('.')
        print(i, firsti, all, string)
    """

#this is the answer for part 2, it's just a derivation from the last printed statement
# generatoin 104 has a value of 6968 and the increment between the previous ones was 67 so if there were an extra part
# which there wasnt in my case it would have been found through_ 6968 - 104 * 67... Dont really care about the ugliness...
print('Part 2: ', (50000000000)*67 + 0) 
#!/usr/bin/python
from os.path import dirname, join
from collections import defaultdict
import numpy as np
import re

parentDir = dirname(__file__)
path = join(parentDir, 'input-19.txt')
file = open(path, 'r')
input = file.read().splitlines()

actions = {
    'addr': lambda vals, val_1, val_2: vals[val_1] + vals[val_2],
    'addi': lambda vals, val_1, val_2: vals[val_1] + val_2,
    'mulr': lambda vals, val_1, val_2: vals[val_1] * vals[val_2],
    'muli': lambda vals, val_1, val_2: vals[val_1] * val_2,
    'banr': lambda vals, val_1, val_2: vals[val_1] & vals[val_2],
    'bani': lambda vals, val_1, val_2: vals[val_1] & val_2,
    'borr': lambda vals, val_1, val_2: vals[val_1] | vals[val_2],
    'bori': lambda vals, val_1, val_2: vals[val_1] | val_2,
    'setr': lambda vals, val_1, val_2: vals[val_1],
    'seti': lambda vals, val_1, val_2: val_1,
    'gtir': lambda vals, val_1, val_2: 1 if val_1 > vals[val_2] else 0,
    'gtri': lambda vals, val_1, val_2: 1 if vals[val_1] > val_2 else 0,
    'gtrr': lambda vals, val_1, val_2: 1 if vals[val_1] > vals[val_2] else 0,
    'eqir': lambda vals, val_1, val_2: 1 if val_1 == vals[val_2] else 0,
    'eqri': lambda vals, val_1, val_2: 1 if vals[val_1] == val_2 else 0,
    'eqrr': lambda vals, val_1, val_2: 1 if vals[val_1] == vals[val_2] else 0
}

ip = int(re.findall(r'(\d+)', input[0])[0])
program = input[1:]
rgster = [0,0,0,0,0,0]

def run_program(ip, program, rgster):
    while True:
        command = program[rgster[ip]]
        op, vals = command.split(' ', 1)
        a,b,c = list(map(int, vals.split(' ')))
        rgster[c] = actions[op](rgster, a, b)

        if (rgster[ip] + 1) >= len(program):
            break
        rgster[ip] += 1
    return rgster

part1 = run_program(ip, program, rgster)
print('Part 1: ', part1[0])
part2 = [1,0,0,0,0,0]
#part 2 will run for quite a while, it is a better idea to wait until there seems 
#to be an inifite loop and look at the debug values
#print('Part 2:', run_program(ip, program, part2))

"""
My solution for part2:

After a while the program starts looping through just a few of
the steps, breaking out at too big iterations for it being worth letting to program run.
With my input the looping starts at
[0, 10551311, 1, 1, 4, 1]
after this index 1 never changes.

The only times the loop "breaks" and the ip pointer gets a value outside of the inner loop is 1) when
index 5 > index 1. Theese times the value of index 2 is increased by 1.
And 2) when index 3 == index 1 evaluates as true.
Index 3 gets its value in each loop from the the multiplication of 2 and 5.
The product of 2 and 5 evaluates to 10551311 on only 4 occasions.
     [1] 2: 1 and 5: 10551311
     [2] 2: 431 and 5: 24481
     [3] 2: 24481 and 5: 431
     [4] 2: 10551311 and 5: 1
At these occasions addr 200 is also evaluated (this is the only opcode that introduces new values to index 0)
     [1] 2: 1 and 0: 0 => 1 + 0 = 0 => 0:1
     [2] 2: 431 and 0: 1 => 431 + 1 = 432 => 0: 432
     [3] 2: 24481 and 0: 432 => 24481 + 432 => 24913 => 0: 24913
     [4] 2: 10551311 and 0: 24913 => 10576224 => 0: 10576224

After this there are no more opportunities to increase the value of index 0 and when the program finally ends
1057224 will be the value of index 0
"""
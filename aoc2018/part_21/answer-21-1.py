#!/usr/bin/python
from os.path import dirname, join
from collections import defaultdict
import numpy as np
import re

parentDir = dirname(__file__)
path = join(parentDir, 'input-21.txt')
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
    for i in range(100000):
        command = program[rgster[ip]]
        op, vals = command.split(' ', 1)
        a,b,c = list(map(int, vals.split(' ')))
        if rgster[1] == 29:
            print(rgster[5])
        rgster[c] = actions[op](rgster, a, b)
        if (rgster[ip] + 1) >= len(program):
            break
        rgster[ip] += 1
    return rgster[5]

def loop(ip, program, rgster):
    vals = []
    rgster_ = rgster.copy()
    for i in range(1000):
        rgster_[0] = i
        val = run_program(ip, program, rgster_)
        rgster_ = rgster.copy()
        vals.append(val)
    return vals

result = loop(ip, program, rgster)
smallest = min(result)
print(f'{smallest}')

#Part 2 not done

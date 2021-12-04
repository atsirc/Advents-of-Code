#!/usr/bin/python
from os.path import dirname, join
from collections import defaultdict
import numpy as np
import re

parentDir = dirname(__file__)
path = join(parentDir, 'input16.txt')
file = open(path, 'r')
input = file.read()
part_1, part2 = input.split('\n\n\n')
part_1 = re.sub( r'[a-zA-Z]*:', '', part_1).splitlines()
part_1 = list(filter(('').__ne__, part_1))
part_1 = [part.strip() for part in part_1]
cases = [part_1[i-3:i] for i in range(3, len(part_1)+1, 3)]

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

all_matched_codes = defaultdict(list)
total = 0
for case in cases:
    before, opcode, after = case
    vals = eval(before)
    opcode = list(map(int, opcode.split(' ')))
    end_result = eval(after)[opcode[3]]
    matches = 0
    for key, action in actions.items():
        result = action(vals, opcode[1], opcode[2])
        if result == end_result:
            all_matched_codes[opcode[0]].append(key)
            matches += 1

    total += 1 if matches >= 3 else 0

print(f'Part 1: {total}')

all_matched_codes = {code: list(set(matches)) for code, matches in all_matched_codes.items()}

opcodes = {}
all_found = False
while not all_found:
    for key, lis_t in all_matched_codes.items():
        vals = list(opcodes.values())
        unmatched = np.setdiff1d(lis_t, vals)
        if len(unmatched) == 1:
            opcodes[key] = unmatched[0]
            if len(opcodes) == len(all_matched_codes):
                all_found = True

part2 = part2.strip().splitlines()
regs = [0,0,0,0]
for line in part2:
    opcode, a, b, c = map(int, line.split(' '))
    result = actions[opcodes[opcode]](regs, a, b)
    regs[c] = result

print(f'Part 2: {regs[0]}')
#!/bin/python3
from os.path import dirname, join
import re

parentDir = dirname(__file__)
path = join(parentDir, 'input16.txt')
file = open(path, 'r')
input = file.read()
part_1, part2 = input.split('\n\n\n')
#part_1 = "Before: [3, 2, 1, 1]\n9 2 1 2\nAfter:  [3, 2, 2, 1]\n\nBefore: [1, 0, 1, 3]\n10 2 3 3\nAfter:  [1, 0, 1, 0]"
part_1 = re.sub( r'[a-zA-Z]*:', '', part_1).splitlines()
part_1 = list(filter(('').__ne__, part_1))
part_1 = [part.strip() for part in part_1]
cases = [part_1[i-3:i] for i in range(3, len(part_1)+1, 3)]

total = 0
for item in cases:
    initial, opcode, result = item
    initial = eval(initial)
    opcode = list(map(int, opcode.split(' ')))
    results = []
    math_signs = ['+', '*', '&', '|']
    
    #math
    for char in math_signs:
        r, i = [initial.copy() for j in range(2)]
        str_1 = str(initial[opcode[1]]) + char + str(initial[opcode[2]])
        r[opcode[3]] = eval(str_1)
        str_2 = str(initial[opcode[1]]) + char + str(opcode[2])
        i[opcode[3]] = eval(str_2)
        results.append(str(i))
        results.append(str(r))

    #assignment
    sr, si = [initial.copy() for j in range(2)]
    sr[opcode[3]] = initial[opcode[1]]
    si[opcode[3]] = opcode[1]
    results.append(str(si))
    results.append(str(sr))

    #thruthy values
    boolean_signs = ['>', '==']
    for char in boolean_signs:
        ir, ri, rr = [initial.copy() for j in range(3)]
        str_1 = str(opcode[1]) + char + str(initial[opcode[2]])
        ir[opcode[3]] = 1 if eval(str_1) else 0
        str_2 = str(initial[opcode[1]]) + char + str(opcode[2])
        ri[opcode[3]] = 1 if eval(str_2) else 0
        str_3 = str(initial[opcode[1]]) + char + str(initial[opcode[2]])
        rr[opcode[3]] = 1 if eval(str_3) else 0
        results.append(str(ir))
        results.append(str(ri))
        results.append(str(rr))
    matches = sum([res == result for res in results])
    total += 1 if matches >= 3 else 0

print(item)
print(total)


#!/usr/bin/python3

from os.path import dirname, join

parentDir = dirname(__file__)
path = join(parentDir, './input1.txt')
file = open(path, 'r')
numbersStr = file.read()
numbers = numbersStr.rstrip('\n').split('\n')

result = 0

for i, val in enumerate(numbers):
	result += int(val, 0)

print ('Part 1: ', result)

frequencies  = []
curr = i = 0 

while curr not in frequencies:
	frequencies.append(curr)
	curr += int(numbers[i%len(numbers)])
	i += 1

print ('Part 2: ', curr)

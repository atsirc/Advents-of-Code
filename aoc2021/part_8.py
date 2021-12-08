f = open('input_day_8.txt')
lines = [(pattern, output) for pattern, output in (line.split(' | ') for line in f.read().splitlines())]

#one = 2
#four = 4
#seven = 3
#eight = 7


def count_1478_in_output():
    unique_lengths = [2,4,3,7]
    appearances = 0
    for pattern, output in lines:
        for digit in output.split():
            if len(digit) in unique_lengths:
                appearances += 1
    return appearances

# length 5:
# 2: acdeg
# 3: acdfg
# 5: abdfg

# length 6:
# 0: abcefg
# 6: abdefg
# 9: abcdfg

def find_numbers(pattern):
    numbers = dict()
    for num in pattern:
        if len(num) == 2:
            numbers[1] = num
        elif len(num) == 3:
            numbers[7] = num
        elif len(num) == 4:
            numbers[4] = num
        elif len(num) == 7:
            numbers[8] = num
        if len(numbers) == 4:
            break
    for num in pattern:
        if len(num) == 5:
            if all([item in num for item in numbers[1]]):
                numbers[3] = num
            elif len(set(numbers[4]) & set(num)) == 2:
                numbers[2] = num
            else:
                numbers[5] = num
        elif len(num) == 6:
            if all([item in num for item in numbers[4]]):
                numbers[9] = num
            elif all([item in num for item in numbers[7]]):
                numbers[0] = num
            else:
                numbers[6] = num
    return numbers

def get_sum_from_output():
    outputs = list()
    for pattern, output in lines:
        pattern = [sorted(list(num)) for num in pattern.split()]
        output = [sorted(list(num)) for num in output.split()]
        keys = find_numbers(pattern) 
        value = ''
        for num in output:
            for k,v in keys.items():
                if v == num:
                    value += str(k)
                    break
        outputs.append(int(value))
    return sum(outputs)

print(f'Part 1: {count_1478_in_output()}')
print(f'Part 2: {get_sum_from_output()}')

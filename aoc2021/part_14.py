from collections import defaultdict
f = open('input_day_14.txt')

pairs = defaultdict(int)
letters = defaultdict(int)
rules = {}
for line in f.read().splitlines():
    if not pairs:
        for i in range(len(line)-1):
            pair = line[i:i+2]
            pairs[pair] += 1
            letters[line[i]] += 1
        letters[line[-1]] += 1
    elif len(line) == 0:
        continue
    else:
        parts = line.split(' -> ')
        rules[parts[0]] = parts[1]

def polymerize(current):
    keys = [key for key, i in current.items() if i > 0]
    new_dict = defaultdict(int)
    for key in keys:
        f, s = key[0],key[1]
        new = rules[key]
        letters[new] += 1 * current[key]
        new_dict[f+new] += 1 * current[key]
        new_dict[new + s] += 1 * current[key]
    return new_dict

for i in range(10):
    pairs = polymerize(pairs)

least_common = min(letters.values())
most_common = max(letters.values())

print(f'Part 1: {most_common - least_common}')

# 30 because 40 - 10 ... that is pairs has already gone through 10 steps i part 1
for i in range(30):
    pairs = polymerize(pairs)

least_common = min(letters.values())
most_common = max(letters.values())

print(f'Part 2: {most_common - least_common}')

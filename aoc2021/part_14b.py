from collections import defaultdict, Counter
f = open('input_day_14.txt')

# Brute force version, done for debugging reasons

pairs = defaultdict(int)
string = ''
rules = {}
for line in f.read().splitlines():
    if not string:
        string = line
    elif len(line) == 0:
        continue
    else:
        parts = line.split(' -> ')
        rules[parts[0]] = parts[1]

def polymerize(current):
    string = ''
    for i in range(len(current)-1):
        pair = current[i:i+2]
        new = rules[pair]
        string += pair[0] + new
        if i == (len(current) - 2):
            string += pair[1]
    return string

    
for i in range(10):
    string = polymerize(string)
    letters = Counter(string)
    print(letters)

letters = Counter(string)
print(letters)
# print(letters)
least_common = min(letters.values())
most_common = max(letters.values())

print(f'Part 1: {most_common - least_common}')

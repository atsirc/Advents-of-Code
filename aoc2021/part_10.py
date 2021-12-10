f = open('input_day_10.txt')
lines = [line for line in f.read().splitlines()]

pairs = { '(': ')', '[': ']', '{': '}', '<': '>' }
value = { ')': 3, ']': 57, '}': 1197, '>': 25137 }
score = { ')': 1, ']': 2, '}': 3, '>': 4}

#   Creating lists of corrupted an incomplete... 
#   turns out pretty unnescessary to save original line

corrupted, incomplete = list(), list()
for line in lines:
    line_ = ''
    corr = False
    for c in line:
        if not line_:
            line_ += c
        elif pairs[line_[-1]] == c:
            line_ = line_[:-1]
        elif c in pairs.values():
            corr = True
            corrupted.append((c, line))
            break
        else:
            line_ += c 
    if not corr: 
        incomplete.append((line_,line))

print(f'Part 1: {sum([value[c] for c, line in corrupted])}')

# Part 2
totals = list()
for unfinished, full in incomplete:
    total = 0
    for c in reversed(unfinished):
        c_pair = pairs[c]
        total *= 5
        total += score[c_pair]
    totals.append(total)

totals = sorted(totals)
middle = int((len(totals)-1)/2)

print(f'Part 2: {totals[middle]}')

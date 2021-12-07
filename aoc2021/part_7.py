f = open('input_day_7.txt')
crabs = [int(crab)for crab in f.read().split(',')]
crabs = sorted(crabs)

def move_crabs(part2=False):
    least_steps = None 
    for pos in range(crabs[0], crabs[-1]):
        steps = 0
        if not part2:
            steps = sum([abs(crab - pos) for crab in crabs]) 
        else:
            #steps = sum([sum(range(0, abs(crab-pos)+1)) for crab in crabs])
            steps = sum(list(map(lambda x: abs(pos-x)*(abs(pos-x)+1)/2, crabs)))
        least_steps = steps if not least_steps else min(least_steps, steps)
    return least_steps

print(f'Part 1: {move_crabs()}')
print(f'Part 2: {move_crabs(True)}')

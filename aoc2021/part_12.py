from collections import defaultdict
from collections import Counter
input = open('input_day_12.txt').read().splitlines()
paths = defaultdict(list)
for line in input:
    c1, c2 = line.split('-')
    paths[c1].append(c2) 
    paths[c2].append(c1)

def find_all_paths(point, history, results, part_2=False):
    possibilites = [path for path in paths[point] if path != 'start']
    history += point + ',' 
    for path in possibilites:
        visited = history.split(',')
        if path.lower() == path and path in visited:
            if part_2:
                small_caves_visited = [cave for cave in visited if cave.islower()]
                visited_counts = Counter(small_caves_visited)
                if all(cave == 1 for cave in visited_counts.values()):
                    find_all_paths(path, history, results, part_2)
            continue
        elif path != 'end':
            find_all_paths(path, history, results, part_2)
        else:
            results.append(history+'end')
    return results

res1 = find_all_paths('start', '', [])
res2 = find_all_paths('start', '', [], True) 
print(f'Part 1: {len(res1)}')
print(f'Part 2: {len(res2)}')

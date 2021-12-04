f = open('input_day_2.txt')
inputs = [(parts[0], int(parts[1])) for parts in (line.split(' ') for line in f.read().splitlines())]

#Part 1
def move(movement, point):
  course, distance = movement
  horizontal, depth = point
  if course =='forward':
    horizontal += distance 
  elif course == 'down':
    depth += distance 
  else:
    depth -= distance 
  return (horizontal, depth)

horizontal = 0
depth = 0

for i in inputs:
  horizontal, depth = move(i, (horizontal, depth))

print(horizontal * depth)

#Part 2
def move_with_aim(movement, point):
  course, distance = movement
  horizontal, depth, aim = point
  if course == 'down':
    aim += distance
  elif course == 'up':
    aim  -= distance
  else:
    horizontal += distance
    depth += distance * aim
  return (horizontal, depth, aim)

horizontal = 0
depth = 0
aim = 0

for i in inputs:
  horizontal, depth, aim = move_with_aim( i, (horizontal, depth, aim))

print(horizontal * depth)

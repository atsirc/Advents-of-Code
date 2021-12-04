filename = "input_day_1.txt"
f = open(filename)
measurements = [int(x) for x in f.read().splitlines()]
measurements2 = [measurements[i-2] + measurements[i-1] + measurements[i] for i in range(2, len(measurements))]

def count_increments (measurements):
  increasements = 0
  for i in range(1, len(measurements)):
    if measurements[i] > measurements[i-1]:
      increasements += 1
  print(increasements)

count_increments(measurements)
count_increments(measurements2)

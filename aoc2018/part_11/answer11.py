#!/bin/python3
serial_number = 8561
cap = 300

class Power_unit:
    board = {}

    def __init__(self, serial_number, size):
        self.serial_number = serial_number
        for x in range(1, size + 1):
            for y in range(1, size + 1):
                self.board[f'{x},{y}'] = self.set_power_level(x, y)

    def set_power_level(self, x, y):
        rack_id = x + 10
        power_level = rack_id * y
        power_level = power_level + self.serial_number
        power_level = power_level * rack_id
        power_level = - 5 + (int(str(power_level)[-3]) if len(str(power_level)) >= 3 else 0)
        return power_level

unit = Power_unit(serial_number, cap)
board = unit.board

def checkSquares(size, board_size, board):
    name = {}
    for x in range(1, board_size + 2 - size):
        for y in range(1, board_size + 2 - size):
            sum = 0
            for i in range(size):
                for j in range(size):
                    sum += board[f'{x+i},{y+j}']
            name[f'{x},{y},{size}'] = sum
    return name

small_squares = checkSquares(3, cap, board)
maxKey = max(small_squares, key=small_squares.get)
maxVal = small_squares[maxKey]
print('Part 1:', maxKey, " ", maxVal)

#PART 2
"""This works only because I determined that the best result probably was within the size of 20...
Looking through the reddit I could see I wasn't the only on who came to this conclusion.
Of course there are better ways to handle this problem, though"""

max_key = max(board, key=board.get)
max_val = board.get(max_key)

for i in range(2, 20 + 1):
    squares = checkSquares(i, cap, board)
    this_max_key = max(squares, key=squares.get)
    this_max_val = squares[this_max_key]
    if this_max_val > max_val:
        max_key, max_val = this_max_key, this_max_val

print('Part 2:', max_key, " ", max_val)
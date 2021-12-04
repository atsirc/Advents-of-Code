#!/bin/python3

def findHighestScore(playerCount, end):
    linkedDict = {0:0}
    reversedDict = {0:0}
    players = {}
    currentPlayer = 0
    current = 0

    for new in range(1, endMarble+1):
        currentPlayer = currentPlayer % playerCount + 1

        if new % 23 == 0:
            found = current
            for j in range(7):
                found = reversedDict[found]
            toBeRemoved = found
            players[currentPlayer] = players.get(currentPlayer, 0) + new + toBeRemoved
            newNext = linkedDict[toBeRemoved]
            new = reversedDict[toBeRemoved]
            current = newNext

        else:
            next = linkedDict[current]
            reversedDict[new] = next
            newNext = linkedDict[next]
            linkedDict[next] = new
            current = new
        
        linkedDict[new] = newNext
        reversedDict[newNext] = new
    
    return max(players.values())

playerCount = 403
endMarble = 71920

print("Part 1: ", findHighestScore(playerCount, endMarble))
endMarble *= 100
print("Part 2: ", findHighestScore(playerCount, endMarble))

#runs ok, is basic. Learned of deque, so next time I'll probably try that instead
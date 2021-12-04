#!/bin/python3
playerCount = 403
endMarble = 71920

linkedDict = {0:0}
players = {}
currentPlayer = 0
marbleList = [0]
currentIndex = 0
current = 0

for i in range(1, endMarble+1):
    currentPlayer = currentPlayer % playerCount + 1

    if i % 23 == 0:
        players[currentPlayer] = players.get(currentPlayer, 0) + i
        currentIndex = currentIndex - 7
        if currentIndex < 0:
            currentIndex = len(marbleList) + currentIndex
        toBeRemoved = marbleList[currentIndex]
        newNext = linkedDict[toBeRemoved]
        i = marbleList[currentIndex-1]
        linkedDict.pop(toBeRemoved)
        marbleList.remove(toBeRemoved)
        players[currentPlayer] = players[currentPlayer] + toBeRemoved

    else:
        next = linkedDict[current]
        newNext = linkedDict[next]
        linkedDict[next] = i
        currentIndex = (currentIndex + 2) % len(marbleList)
        marbleList.insert(currentIndex, i)

    current = marbleList[currentIndex]
    linkedDict[i] = newNext
    #print (marbleList)

print(max(players.values()))
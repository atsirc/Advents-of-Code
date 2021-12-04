#!/usr/bin/python3

import re
from os.path import dirname, join
from functools import cmp_to_key

file = './input4.txt'
dir = dirname(__file__)
path = join(dir, file)
file = open(path)
input = file.read().splitlines()

def getDate(string):
    findDate = r'\[(.*)\]'
    date = re.match(findDate, string)[1]
    dateParts = r'\D'
    dateKeys = ['year', 'month', 'day', 'hour', 'minute']
    date = dict(zip(dateKeys, map(int, re.split(dateParts, date))))
    return date

def sortByTime(string, otherString):
    date1 = getDate(string)
    date2 = getDate(otherString)
    dateKeys = date1.keys()

    for key in dateKeys:
        if date1[key] != date2[key]:
            return 1 if date1[key] > date2[key] else -1

sortingMethod = cmp_to_key(sortByTime)
input.sort(key=sortingMethod)

guards = {}
i = 0
while i < len(input):
    note = input[i]
    pattern = r'#\d*'
    id = re.search(pattern, note)[0]
    guard = guards.get(id, {})
    i += 1
    sub = input[i:]
    j = 0
    while j < len(sub):
        if 'falls asleep' in sub[j]:
            start = sub[j]
            asleep = getDate(start)
            stop = sub[j+1]
            wakesUp = getDate(stop)
            for min in range(asleep['minute'], wakesUp['minute']):
                guard[min] = guard.get(min, 0) + 1
            j += 2
        else:
            break
    i += j
    if (bool(guard)):
        guards[id] = guard


def getMax(guards, array, part):
    sleepiest = max(array, key=array.get)
    timetable = guards.get(sleepiest)
    amount = max(timetable, key=timetable.get)
    print ('Part ' + part + ': ' , int(sleepiest.replace('#','')) * int(amount))


#PART 1
totalMinutesSlept = [sum(guards[guard].values()) for guard in guards.keys()]
guardsSleep = dict(zip(guards.keys(), totalMinutesSlept))
getMax(guards, guardsSleep, '1')

#PART 2
favoriteMinute = [max(guards[guard].values()) for guard in guards.keys()]
guardsFavoriteMin = dict(zip(guards.keys(), favoriteMinute))
getMax(guards, guardsFavoriteMin, '2')
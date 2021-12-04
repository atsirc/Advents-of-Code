const min = 130254;
const max = 678275;

const sameAdjacent = (string) => {
  // PART 1:
  //  return (string.match(/(\d)\1/g)) ? true : false;
    const matches = string.match(/(\d)\1+/g);
    if (matches != null) {
        const matchesSizes = matches.map(n => n.length);
        return matchesSizes.indexOf(2) > -1;
    }
    return false;
}
const isInGrowingOrder = (string) => {
    let numbers = string.split("").map(Number);
    numbers = numbers.sort((a,b)=> a-b);
    return numbers.join("") === string;
}

const possiblePasswords = new Array()
for (let i = min; i < max; i++) {
    const numberAsString = i.toString();
    if (sameAdjacent(numberAsString)) {
        if (isInGrowingOrder(numberAsString)) {
            possiblePasswords.push(parseInt(numberAsString));
        }
    }
}

console.log("Part 2: " + possiblePasswords.length);
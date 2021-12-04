const fs = require('fs');
const url = 'dag_2.txt';
const lines = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(n => parseInt(n));

const loop = (lines, noun, verb) => {
    lines[1] = noun;
    lines[2] = verb;
    for (let i = 0; i < lines.length-4; i+= 4) {
        let command = lines[i];
        if  (command == 99) {
            break;
        }
        let a = lines[lines[i+1]];
        let b = lines[lines[i+2]];
        let result = (command == 1) ? a + b : a * b;
        lines[lines[i+3]] = result;
    }
    return lines;
}

const findNounVerb = (integer, array) => {
    for (i = 0; i < 100; i++) {
        for (j = 0; j < 100; j++) {
            const copy = array.map(n => n);
            const result = loop(copy, i, j);
            if ( result[0] == integer ) {
                return copy;
            }
        }
    }
}

const copy  = lines.map(n=>n);
const part1 = loop(copy, 12, 2)[0];
console.log("PART 1: " + part1);

const part2 = findNounVerb(19690720, lines);
console.log("Part 2: noun " + part2[1] + " verb " + part2[2] + " result " + (part2[1]*100 + part2[2]) )
const fs = require('fs');
const url = 'dag_1.txt';
const lines = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n');

const values = lines.map( line => Math.floor(line/3) - 2 );
console.log('Part 1: ' + values.reduce((a,b) => a+b));

const requirements = (acc, amount) => {
    let sum = amount;
    while (amount > 0) {
        amount = Math.floor(amount/3) - 2;
        sum += amount > 0 ? amount : 0;
    }
    return acc + sum;
}
console.log('Part 2: ' + values.reduce(requirements, 0));

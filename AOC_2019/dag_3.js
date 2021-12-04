const fs = require('fs');
const url = 'dag_3.txt';
const [cord1, cord2] = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n').map(n => n.split(","));
const directions = {
    'U' : { x:0, y:1},
    'D' : { x:0, y:-1},
    'L' : { x:-1,y:0},
    'R' : { x:1, y:0}
}
const cord1Movements = ["0,0"];
const cord2Movements = ["0,0"];

const move = (position, move) => {
    const direction = directions[move[0]];
    const steps = move.substr(1);
    let [x,y] = position[position.length-1].split(",").map(Number);
    for (let i = 0; i < steps; i++ ) {
        x += direction.x;
        y += direction.y;
        position.push(x + "," + y );
    }
}

cord1.forEach(instruction => move(cord1Movements, instruction));
cord2.forEach(instruction => move(cord2Movements, instruction));

let intersections = [];

for (const coords of cord1Movements) {
    if (cord2Movements.includes(coords)) {
        intersections.push(coords);
    }
}
intersections.shift();
const answers = intersections.map(n => {
    let [x,y] = n.split(",").map(Number);
    return Math.abs(x) + Math.abs(y)}).sort((a,b) => a - b);
console.log("Part 1: " + answers[0]);

const steps = [];

for (const intersection of intersections) {
    const stepsCord1 = cord1Movements.indexOf(intersection);
    const stepsCord2 = cord2Movements.indexOf(intersection);
    steps.push((stepsCord1+stepsCord2));
}

const answer2 = steps.sort((a,b) => a - b);
console.log("Part 2: " + answer2[0]);
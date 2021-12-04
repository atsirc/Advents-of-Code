const IntComputer = require('./intcomputer.js');
const fs = require('fs'); // { readFileSync } from "fs";
const url = 'dag_11.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(Number);

const Robot = (program) => {
    const computer = IntComputer(program);
    const directions = {
        'U': {'x': 0, 'y': 1},
        'H': {'x': 1, 'y': 0},
        'N': {'x': 0, 'y': -1},
        'V': {'x': -1, 'y': 0}
    };
    let facing = 0;
    let currentPosition = {'x': 0, 'y': 0};

    const getPosition = () => {
        return Object.values(currentPosition).toString();
    }
    const move = (newFacing) => {
        facing += newFacing === 0 ? 1: -newFacing;
        const direction = Object.keys(directions).join('').substr(facing % 4, 1);
        currentPosition.x += directions[direction].x;
        currentPosition.y += directions[direction].y
    }
    const run = (color) => {
        const newColor = computer.runCode([color]);
        const direction = computer.runCode();
        const oldPosition = getPosition();
        move(direction);
        return [oldPosition, newColor];
    }
    const isTurnedOn = () => {
        return computer.isRunning();
    }
    return Object.freeze({getPosition, run, isTurnedOn});

}
const runProgram = (robot, int) => {
    const grid = {};
    let currentColor = int;
    while (robot.isTurnedOn()) {
        const [key, color] = robot.run(currentColor);
        grid[key] = color;
        currentColor = grid[robot.getPosition()] || 0;
    }
    return grid;
}

const readMessage = (grid) => {
    let allXs = Object.keys(grid).map(n => {
        const [x,y] = n.split(',');
        return parseInt(x);
    });
    let allYs = Object.keys(grid).map(n => {
        const [x,y] = n.split(',');
        return parseInt(y);
    });
    const minx = Math.min(...allXs);
    const miny = Math.min(...allYs);
    const maxx = Math.max(...allXs);
    const maxy = Math.max(...allYs);
    
    const chars = {0: ' ', 1: '▓'};
    
    for (let i = maxy; i >= miny; i-- ) {
        let str = "";
        for (let j = maxx; j >= minx; j-- ) {
            let char = grid[j+','+i] || 0;
            str += chars[char];
        }
        console.log(str);
    }
}

//init
const robot = Robot(input.map(n=>n));
const part1 = runProgram(robot, 0);
console.log("Part 1: "+ Object.keys(part1).length);

const robot2 = Robot(input.map(n=>n));
const part2 = runProgram(robot2, 1);
console.log("Part 2: ")
readMessage(part2)
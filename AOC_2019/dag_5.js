const fs = require('fs');
const url = 'dag_5.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(Number);

const runCode = (start, input) => {
    let jump;
    for (let i = 0; i < input.length; i+= jump) {
        let command = input[i];
        const opCode = command % 100;
        switch (opCode) {
            case 99: return;
            case 3: input[input[i+1]] = start;
                jump = 2;
                break;
            default: command = command.toString().padStart(4,0);
                jump = handleMultiply(command, input, i);
        }
    }
}

const handleMultiply = (command, input, index) => {
    const opCode = command % 100;
    const params = command.substring(0, command.length-2).split('').reverse().map( (n, i) => {
        return (parseInt(n) === 0) ? input[ input[ index + 1 + i ] ] : input[ index + 1 + i ];
    });
    let increment = 4;
    switch(opCode) {
        case 1: input[input[index+3]] = params[0] + params[1];
            break;
        case 2: input[input[index+3]] = params[0] * params[1];
            break;
        case 4: increment = 2;
            console.log(params[0]);
            break;
        case 5: increment = (params[0] != 0) ? params[1] - index: 3;
            break;
        case 6: increment = (params[0] == 0) ? params[1] - index: 3;
            break;
        case 7: input[input[index+3]] = (params[0] < params[1]) ? 1 : 0;
            break;
        case 8: input[input[index+3]] = (params[0] == params[1]) ? 1 : 0;
    }
    return increment;
}
const copy1 = input.map(n=>n);
const part1 = runCode(1, copy1);
const copy2 = input.map(n=>n);
const part2 = runCode(5, copy2);
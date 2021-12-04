const fs = require('fs');
const url = 'dag_7.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(Number);

const IntComputer = (input) => {
    const instructions = input;
    let isOn = true;
    let i = 0;
    const runCode = (start) => {
        let increment;
        for (i; i < instructions.length; i+= increment) {
            let command = instructions[i];
            const opCode = command % 100;
            command = command.toString().padStart(4,0);
            const params = command.substring(0, command.length-2).split('').reverse().map( (val, j) => {
                return (parseInt(val) === 0 ) ? instructions[ instructions[ i + 1 + j ] ] : instructions[ i + 1 + j ];
            });

            increment = 4;
            switch (opCode) {
                case 99: isOn = false;
                    return;
                case 1: instructions[instructions[ i+3 ]] = params[0] + params[1];
                    break;
                case 2: instructions[instructions[ i+3 ]] = params[0] * params[1];
                    break;
                case 3:
                    instructions[instructions[ i+1 ]] = start.shift();
                    increment = 2;
                    break;
                case 4: i +=2;
                    return params[0];
                case 5: increment = (params[0] != 0) ? params[1] - i: 3;
                    break;
                case 6: increment = (params[0] == 0) ? params[1] - i: 3;
                    break;
                case 7: instructions[instructions[ i+3 ]] = (params[0] < params[1]) ? 1 : 0;
                    break;
                case 8: instructions[instructions[ i+3 ]] = (params[0] == params[1]) ? 1 : 0;
            }
        }
    }
    const isRunning = () => {
        return isOn;
    }
    return Object.freeze({isRunning, runCode});
};

//To be honest - I googled the permutations function
let findPermutations = (string) => {
    let permutationsArray = [];
    if (!!string.length && string.length < 2 ) {
        return string
      }    
    for (let i = 0; i < string.length; i++) {
        let char = string[i];
        if (string.indexOf(char) != i) {
            continue;
        }
        let remainder = string.slice(0, i) + string.slice(i + 1, string.length);
        for (let permutation of findPermutations(remainder)){
            permutationsArray.push(char + permutation);
        }
    }
    return permutationsArray;
}


//PART 1
const getMaxOutput = (input, combination) => {
    let commands = [0]
    for (let i = 0; i < combination.length; i++) {
        const computer = IntComputer(input.map(n=>n));
        commands.unshift(parseInt(combination[i]));
        const output = parseInt(computer.runCode(commands));
        commands.push(output);
    }
    return commands[0];
}

const p1combinations = findPermutations("01234");
const p1max = p1combinations.map(comb => getMaxOutput(input, comb));
console.log("Part 1: " + Math.max(...p1max));

//PART 2
const getMaxFeedbackOutput = (input, combination) => {
    let max = 0;
    let commands = [0];
    let computers = [];
    let i = 0;
    while (true) {
        if (computers.length < combination.length ) {
            const newComputer = IntComputer(input.map(n => n));
            computers.push(newComputer);     
            commands.unshift(parseInt(combination[i]));
        }
        const computer = computers[i % 5];
        const output = parseInt(computer.runCode(commands));
        commands.push(output);
        max = Math.max(max, commands[0]);
        if (!computer.isRunning()) {
            return max;
        } else {
            i++;
        }
    }
}

const p2combinations = findPermutations("56789");
const p2max = p2combinations.map(comb => getMaxFeedbackOutput(input, comb));
console.log("Part 2: " + Math.max(...p2max));
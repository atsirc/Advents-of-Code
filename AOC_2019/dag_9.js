const fs = require('fs');
const url = 'dag_9.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(Number);

const IntComputer = (initialProgram) => {
    const backend = (initialProgram) => {
        const instructions = initialProgram;
        const initSize = initialProgram.length;

        const addMemory = (index) => {
            const length = instructions.length;
            const diff = instructions.length - initSize;
            const newIndexes = index - instructions.length;
            for (let k = 0; k <= newIndexes; k++) {
                instructions[length + k] = k + diff;
            }        
        };
        const get = (index) => {
            if (!(index < instructions.length)) {
                addMemory(index);
            }
            return instructions[index];
        };
        const set = (index, newInt) => {
            if (!(index < instructions.length)) {
                addMemory(index);
            }
            instructions[index] = newInt;
        };
        const size = () => {
            return instructions.length;
        };
        return Object.freeze({get, set, size});
    };

    const program = backend(initialProgram);
    let isOn = true;
    let i = 0;
    let relativeBase = 0;

    const runCode = (input) => {
        let increment;
        for (i; i < program.size(); i += increment) {
            let command = program.get(i);
            const opCode = command % 100;
            command = command.toString().padStart(5,0);
            const params = command.split('').reverse().slice(2,4).map( (val, j) => {
                const mode = parseInt(val);
                switch (mode) {
                    case 0: return program.get( program.get( i + 1 + j ) );
                    case 1: return program.get( i + 1 + j );
                    case 2: return program.get( relativeBase + program.get( i + 1 + j ));
                }
            });

            increment = 4;
            let offset;
            if ( opCode === 3 ) {
                offset = parseInt(command.substring(2,3)) === 0 ? 0 : relativeBase; 
            } else {
                offset = parseInt(command.substring(0,1)) === 0 ? 0 : relativeBase;
            }

            switch (opCode) {
                case 99: isOn = false;
                    return;
                case 1: program.set(program.get( i + 3 ) + offset, params[0] + params[1]);
                    break;
                case 2: program.set(program.get( i + 3 )+ offset,  params[0] * params[1]);
                    break;
                case 3:
                    program.set(program.get( i + 1 ) + offset, input.shift());
                    increment = 2;
                    break;
                case 4: return (offset + params[0]);
                    increment = 2;
                    break;
                case 5: increment = (params[0] != 0) ? params[1] - i: 3;
                    break;
                case 6: increment = (params[0] == 0) ? params[1] - i: 3;
                    break;
                case 7: {const newVal = (params[0] < params[1]) ? 1 : 0;
                    program.set(program.get( i + 3 ) + offset ,  newVal);
                    break;}
                case 8: {const newVal = (params[0] == params[1]) ? 1 : 0;
                    program.set(program.get( i + 3 ) + offset , newVal);
                    break;}
                case 9: relativeBase += params[0];
                    increment = 2;
            }
        }
    };
    const isRunning = () => {
        return isOn;
    };
    return Object.freeze({isRunning, runCode});
};

const svar1 = IntComputer(input.map(n=>n)).runCode([1]);
console.log("Part 1: "+ svar1);
const svar2 = IntComputer(input.map(n=>n)).runCode([2]);
console.log("Part 2: "+ svar2);

const IntComputer = require('./intcomputer.js');
const fs = require("fs");
const url = 'dag_13.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split(',').map(Number);


const Game = (code) => {
    const program = code;
    let computer = IntComputer(program.map(n=>n));
    let coords = {};
    let input = [];
    let maxx = 0;
    let maxy = 0;
    const tiles = {
        0: ' ', // is an empty tile. No game object appears in this tile.
        1: '▓', // is a wall tile. Walls are indestructible barriers.
        2: '▒', // is a block tile. Blocks can be broken by the ball.
        3: '▄', // is a horizontal paddle tile. The paddle is indestructible.
        4: 'o'  // is a ball tile. The ball moves diagonally and bounces off objects.
    };
    const getGridVals = () => {
        const array = []
        while (computer.isRunning()) {
            array.push(computer.runCode());
        }
        return array;
    };
    const setCoordinates = (input) => {
        const coords = {};
        for (let i = 0; i < input.length - 3; i += 3) {
            coords[input[i] +',' + input[i+1]] = input[i+2];
        }
        return coords;
    };
    const draw = () => {
        for (let i = 0; i <= maxy; i++ ) {
            let str = "";
            for (let j = 0; j <= maxx; j++ ) {
                let char = coords[j+','+i] || 0;
                str += tiles[char];
            }
            console.log(str);
        }
    };
    const countTile = (tile) => {
        if (Object.keys(coords).length != 0)
            return Object.values(coords).filter(n => n === tile).length;
    };
    const init = () => {
        input = getGridVals();
        coords = setCoordinates(input);
        let allXs = Object.keys(coords).map(n => {
            const [x,y] = n.split(',');
            return parseInt(x);
        });
        let allYs = Object.keys(coords).map(n => {
            const [x,y] = n.split(',');
            return parseInt(y);
        });

        maxx = Math.max(...allXs);
        maxy = Math.max(...allYs);

        draw();
    };
    const play = () => {
        computer = IntComputer(program.map(n=>n));
        computer.replace(0,2);
        let command = [0];
        let ballX;
        let paddleX;
        let output = [];
        let score = 0;
        do {
            output.push(computer.runCode(command));
            if (output.length === 3) {
                const [x,y,tile] = output.splice(0,3);
                if (x !== -1) {
                    if (tile === 3) {
                        paddleX = x;
                    }
                    if (tile === 4) {
                        ballX = x;
                    }
                    coords[ x+','+y] = tile;
                } else {
                    score = tile;
                }
                command = paddleX > ballX ? [-1]: paddleX < ballX ? [1] : [0];
            }
        } while (computer.isRunning());
        
        draw();
        console.log('\nScore: ' + score);
    };
    return Object.freeze({init, countTile, play});
}

const game = Game(input);
game.init();
console.log("\nPART 1: " +game.countTile(2));
game.play();
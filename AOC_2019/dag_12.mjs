const fs = require("fs");
const url = 'test_dag_12.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n').map(n => { const m = n.match(/(-?[0-9]+)/g); return {'x': parseInt(m[0]), 'y': parseInt(m[1]), 'z': parseInt(m[2])} });
const coords = ['x','y','z'];

const Moon  = (pos) => {
    let position = {'x': pos.x, 'y': pos.y, 'z': pos.z};
    const initial = position;
    let velocity = {'x': 0, 'y': 0, 'z': 0};

    const getPosition = () => {
        return position;
    };
    const getVelocity = () => {
        return velocity;
    };
    const changeVelocity = (newVals) => {
        for (let coord of coords) {
            velocity[coord] += newVals[coord];
        }
    };
    const move = () => {
        for (let coord of coords) {
            position[coord] += velocity[coord];
        }
    };
    const isInitial = (coord) => {
        return initial[coord] === position[coord] && velocity[coord] === 0;
    }
    
    return Object.freeze({getPosition, getVelocity, changeVelocity, move, isInitial});
};

const setNewVelocitys = (moons) => {
    for (const moon of moons) {
        const position = moon.getPosition();
        for (const otherMoon of moons) {
            if (!Object.is(moon, otherMoon)) {
                const otherPosition = otherMoon.getPosition();
                const x = position.x === otherPosition.x ? 0 : position.x > otherPosition.x ? -1 : 1;
                const y = position.y === otherPosition.y ? 0 : position.y > otherPosition.y ? -1 : 1;
                const z = position.z === otherPosition.z ? 0 : position.z > otherPosition.z ? -1 : 1;
                moon.changeVelocity({'x': x, 'y': y, 'z': z});
            }
        }
    }
}

const gravitate = (moons) => {
    setNewVelocitys(moons);
    for (let moon of moons) {
        moon.move();
    }
}

//PART 1
const moons = input.map(n => Moon(n));

for (let i = 0; i < 1000; i++) {
    gravitate(moons);
}

const answer = moons.map(n => {
    const pos = n.getPosition();
    const vel = n.getVelocity();
    return ((Math.abs(pos.x) + Math.abs(pos.y) + Math.abs(pos.z)) * (Math.abs(vel.x) + Math.abs(vel.y) + Math.abs(vel.z)));

}).reduce((a,b) => a+b);

console.log(answer);


//PART 2
const moons2 = input.map(n => Moon(n));

const gcd = (a, b) => a ? gcd(b % a, a) : b
const lcm = (a, b) => a * b / gcd(a, b)

const repeat = {};
  for (let i = 1; coords.some(coord => !repeat[coord]); i++) {
    gravitate(moons2);
    for (let coord of coords) {
        if (!repeat[coord] && moons2.every(m => m.isInitial(coord))) {
            repeat[coord] = i;
        }
    }
  }

  //I have not figured out why my code works if multiplied by 2...
  console.log(coords.map(d => repeat[d]).reduce(lcm)*2);
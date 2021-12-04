const fs = require('fs');
const url = 'test_dag_10.txt';
const lines = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n');
const width = lines[0].length;
const height = lines.length;
const asteroid = '#';

const Asteroid = (x,y) => {
    const position = { 'x': x, 'y': y };
    const observable = [];
    const sorted = [];

    const findCommonDenominator = (x,y) => {
        const found = [];
        for (let i=2; i <= Math.abs(Math.min(x,y)); i++) {
            if (Math.abs(x) % i === 0 && Math.abs(y) % i === 0) {
                found.push(i);
            }
        }
        return found;
    };
    const findFraction= (x,y) => {
        let common = findCommonDenominator(x,y);
        if (common.length > 0) {
            let biggest = Math.max(...common);
            return {'x': (x/biggest), 'y': (y/biggest)};
        } else {
            return { 'x': x, 'y': y };
        }
    };
    const getBlockingPerspective = (x,y) => {
        //bredvid
        if (x === 0) {
            y = y > 0 ? 1 : -1;
        }
        if (y === 0) {
            x = x > 0 ? 1 : -1;
        }
        //diagonal
        if (Math.abs(x) === Math.abs(y)) {
            x = x > 0 ? 1 : -1;
            y = y > 0 ? 1 : -1;
        } else {
            return findFraction(x, y);
       }
       return {'x': x, 'y': y};
    };
    const addIfNew = (x,y) => {
        const point = getBlockingPerspective(x,y);
        if (!observable.includes(point.x+','+point.y)) {
            observable.push(point.x+','+point.y);
        }
    };
    const observedAsteroids = () => {
        return observable;
    };
    const observedTotal = () => {
        return observable.length;
    };
    const sortingAsc = (a,b) => {
        a = (a.x === 0 || a.y === 0 ) ? 0: Math.abs(a.x / a.y);
        b = (b.x === 0 || b.y === 0 ) ? 0: Math.abs(b.x / b.y);
        return a-b;
    };
    const sort = () => {
        const observablePos = observable.map((n) => { const [x,y] = n.split(','); return {'x': parseInt(x), 'y': parseInt(y)}});
        const quarter1 = observablePos.filter((n)=> {
           if (n.x >= 0 && n.y < 0) {
               return n;
           }
        }).sort(sortingAsc);
        const quarter2 = observablePos.filter(n => {
            if (n.x > 0 && n.y >= 0) {
                return n;
            }
        }).sort(sortingAsc);
        //nedvan shift är för att 0llorna skall hamna i rätt ordning
        let first = quarter2.shift()
        quarter2.reverse().unshift(first);
        const quarter3 = observablePos.filter(n => {
            if (n.x <= 0 && n.y > 0) {
                return n;
            }
            
        }).sort(sortingAsc);
        const quarter4 = observablePos.filter(n => {
            if (n.x < 0 && n.y <= 0) {
                return n;
            }
        }).sort(sortingAsc);
        first = quarter4.shift()
        quarter4.reverse().unshift(first);

        quarter3.push(...quarter4);
        quarter2.push(...quarter3);
        quarter1.push(...quarter2);
        sorted.push(...quarter1);
    }
    const findRelative = (index) => {
        if (index < observedTotal()) {
            if (sorted.length === 0) {
                sort();
            }
            const relativePos = sorted[index-1];
            return relativePos;
        }
    }

    return Object.freeze({position, addIfNew, observedTotal, findRelative});
};

const init = (rows) => {
    const asteroids = {};
    for (let j = 0; j < height; j++) {
        for (let i = 0; i < width; i++) {
            if (rows[j][i] === asteroid) {
                asteroids[i+','+j] = Asteroid(i, j) ;
            }
        }
    }
    return asteroids;
};

const findObservable = (current, asteroids) => {
    let offsetX = 1;
    let offsetY = 1;
    const currentPos = asteroids[current].position;
    while ( currentPos.x - offsetX >= 0 || currentPos.x + offsetX < width ||
            currentPos.y - offsetY >= 0 || currentPos.y + offsetY < height) {
        for (let j = -offsetY; j <= offsetY; j++) {
            for (let i = -offsetX; i <= offsetX; i++ ) {
                if (Math.abs(i) < offsetX && Math.abs(j) < offsetY) {
                    continue;
                }
                const x = currentPos.x + i;
                const y = currentPos.y + j;
                if (x >= width || x < 0 || y >= height || y < 0 ) {
                    continue;
                }
                if ( Object.keys(asteroids).includes( x + ',' + y )) {
                    asteroids[current].addIfNew(i,j);
                }
            }
        }
        offsetX++;
        offsetY++;
    }
};

const getAsteroidToRemove = (array, origo, index) => {
    const r = array[origo].findRelative(index);
    const [x,y] = origo.split(',').map(Number);
    let pos;
    let i = 1;
    do {
        pos = (x + i*r.x) + ',' + (y + i*r.y);
        i++;
    }
    while (!Object.keys(asteroids).includes(pos));
    return array[pos];
};

const asteroids = init(lines);

//PART 1
const observable = [];
for (let position of Object.keys(asteroids)) {
    findObservable(position, asteroids);
    observable.push(asteroids[position].observedTotal());
}
const max = Math.max(...observable);
const index = observable.indexOf(max);
const maxPos = Object.keys(asteroids)[index];
console.log('Part 1: '+ maxPos +  ": " + max);

//PART 2
const toBeRemoved = getAsteroidToRemove(asteroids, maxPos, 200);
console.log('Part 2: ' + (toBeRemoved.position.x*100 + toBeRemoved.position.y));
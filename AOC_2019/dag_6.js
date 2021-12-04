const fs = require('fs');
const url = 'dag_6.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n');

const orbits = {};

for (const orbit of input) {
    const parent = orbit.match(/\w+/)[0];
    const child = orbit.match(/[^\)]\w*$/)[0];
    orbits[child] = parent;
}

const findSuperParent = (array) => {
    const parents = Object.values(array);
    for (const parent of parents) {
        if (!orbits.hasOwnProperty(parent))
            return parent;
    }
}

const findOrbits = (parent, cache) => {
    const links = cache[parent] || 0;
    const children = Object.entries(orbits).filter(([k,v]) => v == parent).map(([k, v]) => k);
    for (const child of children) {
        const history = links+1;
        cache[child] = history;
        findOrbits(child, cache);
    }
    return cache;
}
const topParent = findSuperParent(orbits);
const paths = findOrbits(topParent, {});
const sum = Object.values(paths).reduce((a,b) => a+b);

console.log("Part 1: " + sum);

const findParents = (child, cache) => {
    if (orbits.hasOwnProperty(child)) {
        const parent = orbits[child];
        cache.push(parent)
        findParents(parent, cache);
    }
    return cache;
}
let santasParents = findParents('SAN', []);
let myParents = findParents('YOU', []);

santasParents = santasParents.reverse();
myParents = myParents.reverse();

let i = 0;
while (true) {
    if (santasParents[i] !== myParents[i]) break;
    i++;
}

const split = santasParents[i];

console.log("PART 2: " + (paths['SAN'] + paths['YOU'] - 2 * paths[split]));
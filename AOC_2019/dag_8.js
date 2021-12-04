const fs = require('fs');
const url = 'dag_8.txt';
const input = fs.readFileSync(url, {encoding: 'utf-8'});
const width = 25;
const height = 6;

const getLayers = (width, height, _string) => {
    const length = width * height;
    const layers = [];
    let i = 0
    while(i < _string.length) {
        layers.push(_string.substr(i, length));
        i += length;
    }
    return layers;
}

const getMessage = (layers) => {
    const length = layers[0].length;
    let message = '';
    for (let i = 0; i < length; i++) {
        for (let string of layers) {
            if (string[i] != 2) {
                message += string[i];
                break;
            }
        }
    }
    return message;
}

const readMessage = (message, width) => {
    let img = "\n";
    for (let i = 0; i < message.length; i += width) {
        let string = message.substr(i,width);
        string = string.split('').map(n => n==1 ? "@" : " ").join('');
        img += string + "\n";
    }
    return img;
}

const layers = getLayers(width,height, input);

//PART 2 
const message = getMessage(layers);
console.log("PART 2: ");
console.log(readMessage(message, 25))

//PART 1
layers.sort((a,b) => a.match(/0/g).length - b.match(/0/g).length);
console.log("PART 1: " + layers[0].match(/1/g).length * layers[0].match(/2/g).length);
const fs = require('fs');
const url = 'test_dag_14.txt';
const lines = fs.readFileSync(url, {encoding: 'utf-8'}).split('\n');
const instructions = {};
lines.forEach(n => {
    const [ingredients, cake] = n.split('=>').map(n=>n.trim());
    const [amount, key] = cake.split(' ');
    instructions[key] = {'amount': parseInt(amount), 'ingredients': ingredients.split(',').map(n => {
        const [a, k] = n.trim().split(' ');
        return {'ingredient': k.trim(), 'amount': parseInt(a.trim())}})
    }
});

const count = (key, list, loops) => {
    loops = loops || 1;
    const instructionAmount = instructions[key].amount;
    const vals = instructions[key].ingredients;
    const first = vals[0];
    //loops = loops / instructionAmount;
    //loops = (loops % instructionAmount == 0) ? loops / instructionAmount : Math.floor(loops /instructionAmount) +1;

     if (first['ingredient'] !== 'ORE') {
       //loops = (loops % instructionAmount == 0) ? loops / instructionAmount : Math.floor(loops /instructionAmount) +1;
       loops = loops/instructionAmount;  
      for (const val of vals) {
            list = count(val['ingredient'],list, val['amount'] * loops);
        }
    } else {
        if (!list.hasOwnProperty(key)) {
            list[key] = loops;
        } else {
            list[key] += loops;
        }
    }
    return list;
}


const getOREamount = (key, amount) => {
    const instructionsAmount = instructions[key].amount;
    const leftOver = amount % instructionsAmount;
    let realAmount = Math.floor(amount / instructionsAmount);
    realAmount += (leftOver !== 0) ? 1 : 0
    const ores = instructions[key].ingredients[0].amount;
    return ores * realAmount;
}

let ingredientsList = {};
ingredientsList = count('FUEL', ingredientsList);
const ores = Object.entries(ingredientsList).map(([k,v]) => getOREamount(k,v)).reduce((a,b) => a+b);
console.log(ores);


//dela på div
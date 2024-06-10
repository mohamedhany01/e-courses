const [addNums, addManyNums] = require("./phase-1");


function addNums10Timing(increment) {
  const result = [];
  
  let j = increment;

  console.time("addNums");

  for (let i = 1; i <= 10; i++) {
    let startTime = Date.now();

    result.push(addNums(j));
    j += increment;

    let endTime = Date.now();

    console.log(`${endTime - startTime}`);
  }
  console.timeEnd("addNums");


  return result;
}


function addManyNums10Timing(increment) {
  const result = [];

  let j = increment;

  console.time("addManyNums");

  for (let i = 1; i <= 10; i++) {
    let startTime = Date.now();

    result.push(addManyNums(j));
    j += increment;

    let endTime = Date.now();

    console.log(`${endTime - startTime}`);

  }
  console.timeEnd("addManyNums");

  return result;
}


n = 1000000
console.log(`addNums(${n}): `);
addNums10Timing(1000000);

console.log("\n***********\n");

n = 1000
console.log(`addManyNums(${n}): `);
addManyNums10Timing(5000);
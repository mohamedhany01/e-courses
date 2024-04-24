const [addNums, addManyNums] = require("./phase-1");

// Runs `addNums` in 10 increasing increments
function addNums10(increment) {
  const result = [];
  let j = increment;

  for (let i = 1; i <= 10; i++) {
    result.push(addNums(j));
    j += increment;
  }

  return result;
}

// Runs `addManyNums` in 10 increasing increments
function addManyNums10(increment) {
  let result = [];
  let j = increment;

  for (let i = 1; i <= 10; i++) {
    result.push(addManyNums(j));
    j += increment;
  }

  return result;
}

module.exports = [addNums10, addManyNums10];
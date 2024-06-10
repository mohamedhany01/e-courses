function isFive(num) {
  return num === 5;
}

function isOdd(number) {

  if (typeof number !== 'number') {

    throw new Error("Not a number");
  }

  return number % 2 !== 0;
}

function myRange(min, max, step = 1) {
  const result = [];

  for (let i = min; i <= max; i += step) {
    result.push(i);
  }

  return result;
}


module.exports = { isFive, isOdd, myRange };
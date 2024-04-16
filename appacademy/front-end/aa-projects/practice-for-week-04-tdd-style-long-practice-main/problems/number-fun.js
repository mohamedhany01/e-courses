function returnsThree() {
  return 3;
}

function reciprocal(n) {
  
  if (typeof n !== 'number') {
    throw new TypeError("reciprocal must be a number");
  }

  if (n < 1 || n > 1000000) {
    throw new RangeError("reciprocal must be between 1 and 1000000");
  }

  return 1 / n;
}

module.exports = {
  returnsThree,
  reciprocal
};
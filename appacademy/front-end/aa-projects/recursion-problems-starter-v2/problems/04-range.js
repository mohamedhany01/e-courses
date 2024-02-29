/***********************************************************************
Write a recursive function, `range`, that takes a start and an end and returns
an array of all numbers in that range, exclusive. If the end number is less than
the start, return an empty array.

Examples:

range(1, 5); // [1, 2, 3, 4]
range(3, 4); // [3]
range(7, 6); // []
***********************************************************************/

const range = (start, end) => {
  const result = [];

  const getRang = (result, start, end) => {
    debugger;
    if (start === end || end - start < 0) {
      return result;
    } else {
      result.push(start++)
      return getRang(result, start, end);
    }
  }

  return getRang(result, start, end);
}


/**************DO NOT MODIFY ANYTHING UNDER THIS LINE*****************/
try {
  module.exports = range;
} catch (e) {
  module.exports = null;
}

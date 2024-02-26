/******************************************************************************
Write a function named plannedIntersect(firstArr) that takes in an array and
returns a function. When the function returned by plannedIntersect is invoked
passing in an array (secondArr) it returns a new array containing the elements
common to both firstArr and secondArr.


Example 1:
let abc = plannedIntersect(["a", "b", "c"]); // returns a function
console.log(abc(["b", "d", "c"])); // returns [ 'b', 'c' ]

Example 2:
let fame = plannedIntersect(["f", "a", "m", "e"]); // returns a function
console.log(fame(["a", "f", "z", "b"])); // returns [ 'f', 'a' ]

*******************************************************************************/

function plannedIntersect(firstArr) {
  const result = [];

  return function (secondArr) {
    for (let i = 0; i < secondArr.length; i++) {
      const element = secondArr.indexOf(firstArr[i]);

      if (element === -1) continue;

      result.push(firstArr[i]);
    }

    return result;
  }
}

/**************DO NOT MODIFY ANYTHING UNDER THIS  LINE*************************/
try {
  module.exports = plannedIntersect;
} catch (e) {
  return null;
}
/*
    Write a function maxValue(array) that returns the 
    largest value in array. Assume array is an array of numbers.
*/

// O(N)/O(1)
function maxValue(array) {

    if (array.length === 0) return null;

    let max = -Infinity;

    for (let i = 0; i < array.length; i++) {
        max = Math.max(max, array[i]);
    }

    return max;
}

console.log(maxValue([12, 6, 43, 2]));  // => 43
console.log(maxValue([]));  // => null
console.log(maxValue([-4, -10, 0.43]));  // => 0.43

// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
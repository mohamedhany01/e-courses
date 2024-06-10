/*
    Define a function logBetween(lowNum, highNum) that will 
    return an array from lowNum to highNum, inclusive. 
    Inclusive means that the range includes lowNum and highNum.
*/

// O(N)/O(N)
function logBetween(lowNum, highNum) {
    const res = [];

    for (let i = lowNum; i <= highNum; i++) {
        res.push(i);
    }

    return res;
}

console.log(logBetween(-1, 2));  // => [-1, 0, 1, 2]
console.log(logBetween(14, 6));  // => []
console.log(logBetween(4, 6));  // => [4, 5, 6]

// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
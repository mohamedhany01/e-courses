/*
    Write a function printReverse(min, max) that returns 
    an array of all numbers from max to min (exclusive), in reverse order.
*/

// O(N)/O(N)
function printReverse(lowNum, highNum) {
    const res = [];

    for (let i = highNum - 1; i > lowNum; i--) {
        res.push(i);
    }

    return res;
}

console.log(printReverse(13, 18));  // => [17, 16, 15, 14]
console.log(printReverse(90, 94));  // => [93, 92, 91]

// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
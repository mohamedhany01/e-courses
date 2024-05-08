/*
    Define a function fizzBuzz(max) that takes a number 
    and returns an array of every number from 0 to max 
    that is divisible by either 3 or 5, but not both.
*/

// O(N)/O(N)
function fizzBuzz(max) {
    const res = [];

    for (let i = 1; i < max; i++) {
        const isDiv3 = i % 3 === 0;
        const isDiv5 = i % 5 === 0;

        if ((isDiv3 || isDiv5) && !(isDiv3 && isDiv5)) {
            res.push(i)
        }
    }

    return res;
}

console.log(fizzBuzz(20));  // => [3, 5, 6, 9, 10, 12, 18]


// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
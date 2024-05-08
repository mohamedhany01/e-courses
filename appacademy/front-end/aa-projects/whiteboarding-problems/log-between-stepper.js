/*
    Write a function logBetweenStepper(min, max, step) 
    that takes in three numbers as parameters. The function 
    should return an array of numbers between min and max at step intervals.
*/

// O(N)/O(N)
function logBetweenStepper(min, max, step) {
    const res = [];

    for (let i = min; i <= max;) {
        res.push(i);
        i = i + step;
    }

    return res;
}

console.log(logBetweenStepper(5, 9, 1));  // => [5, 6, 7, 8, 9]
console.log(logBetweenStepper(-10, 15, 5));  // => [-10, -5, 0, 5, 10, 15]

// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
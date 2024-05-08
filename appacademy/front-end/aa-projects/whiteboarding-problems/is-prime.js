/*
    Define a function isPrime(number) that returns true if 
    number is prime. Otherwise, false. Assume number is a positive integer.
*/

// O(N)/O(1)
function isPrime(number) {
    if (number === 1) return false;

    for (let i = 2; i < number; i++) {
        if (number % i === 0) {
            return false;
        }
    }


    return true;
}

console.log(isPrime(2));  // => true
console.log(isPrime(10));  // => false
console.log(isPrime(11));  // => true
console.log(isPrime(9));  // => false
console.log(isPrime(2017));  // => true

// https://my.appacademy.io/lessons/problems/75f5d4f5/practices/whiteboarding-problems/3edb2818
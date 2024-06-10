// Your code here
const isPrime = function (num) {
    let counter = 0;

    for (let i = 1; i <= num; i++) {

        if (num % i === 0) {
            counter++;

            if (counter > 2) {
                return false;
            }
        }

    }

    return true;
}


const prevPrime = function (number) {
    let counter = number;

    while (counter > 2) {
        counter--;

        if (isPrime(counter)) {
            return counter
        };
    }

    return null;
}



console.log(prevPrime(32)); // 31
console.log(prevPrime(33)); // 31
console.log(prevPrime(14)); // 13
console.log(prevPrime(7));  // 5
console.log(prevPrime(4));  // 3
console.log(prevPrime(2));  // null
console.log(prevPrime(1));  // null

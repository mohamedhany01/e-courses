const readline = require('node:readline');

const {
    stdin: input,
    stdout: output
} = require('node:process');

const rl = readline.createInterface({
    input,
    output
});

let secretNumber = null;
let numAttempts = null;

function checkGuess(number) {
    if (number > secretNumber) {
        console.log("Too high.");
        return false;
    } else if (number < secretNumber) {
        console.log("Too low.");
        return false;
    } else {
        console.log("Correct!");
        return true;
    }
}

function askGuess() {

    if (numAttempts === 0) {
        console.log('\nYou Lose');
        rl.close();
        return;
    }

    rl.question('Enter a guess: ', (answer) => {

        const guess = Number(answer);
        const isValidGuess = checkGuess(guess);

        if (isValidGuess) {
            console.log('You win!');
            rl.close();
            return;
        }

        if (!isValidGuess) {

            numAttempts--;

            askGuess();
        }
    });
}

function randomInRange(min, max) {
    const doubleNumber = Math.random() * (max - min) + min;
    return Math.floor(doubleNumber);
}

function askRange() {
    rl.question('Enter a max number: ', (max) => {

        const maxNumber = Number(max);

        rl.question('Enter a min number: ', (min) => {

            const minNumber = Number(min);

            secretNumber = randomInRange(minNumber, maxNumber);

            console.log(`I'm thinking of a number between ${minNumber} and ${maxNumber}...`);

            askGuess();

        });
    });
}

function askLimit() {
    rl.question('Enter limit number: ', (limit) => {
        numAttempts = Number(limit);

        askRange();
    });
}

askLimit(); // Starting point
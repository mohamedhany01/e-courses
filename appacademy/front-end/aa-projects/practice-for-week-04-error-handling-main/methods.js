function sum(array) {
    try {

        if (!Array.isArray(array)) {
            throw new TypeError("Not an array!");
        }

    } catch (error) {
        console.log(error.message);

        return -1;
    }

    let sum = 0;

    for (let i = 0; i < array.length; i++) {
        sum += array[i];
    }

    return sum;
}

function sayName(str) {
    try {

        if (typeof str !== "string") {

            throw new TypeError("Invalid name! Must be a string!");
        }

    } catch (error) {
        console.log(error.message);

        return;
    }

    console.log(str);
}

function greet(greeting) {

    try {

        if (!greeting) {

            throw new Error("There was no greeting given.");
        }

    } catch (e) {

        console.log("Hello World!");

        return;
    }

    console.log(greeting);

}

module.exports = {
    sum,
    sayName,
    greet
}
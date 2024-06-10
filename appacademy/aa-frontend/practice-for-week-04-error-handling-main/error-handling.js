const { sum, sayName, greet } = require("./methods");

// 1.
let res = sum(null);
console.log(res);

// 2.
// tests
sayName("Alex");
sayName(1);


// 3.
greet(null);
greet("Good Morning!");

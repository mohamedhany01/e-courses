const Employee = require('./employee');

const johnWick = new Employee('John Wick', 'Dog Lover');

setTimeout(function() {
    johnWick.sayName();
}, 2000);

setTimeout(function() {
    johnWick.sayOccupation()
}, 3000);
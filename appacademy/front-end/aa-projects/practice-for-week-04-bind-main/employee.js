class Employee {
    constructor(name, occupation) {
        this.name = name;
        this.occupation = occupation;
    }

    sayName() {
        setTimeout(function () {
            console.log(`${this.name} says hello`);
        }.bind(this), 2000);
    }

    sayOccupation() {
        setTimeout(function () {
            console.log(`${this.name} is a ${this.occupation}`);
        }.bind(this), 3000);
    }
}

module.exports = Employee;
class Employee {
    constructor(name, salary, title, manager) {
        this.name = name;
        this.salary = salary;
        this.title = title;
        this.manager = manager ? manager : null;

        if (this.manager && this.manager instanceof Employee) {
            this.manager.addEmployee(this);
        }
    }

    calculateBonus(multiplier) {
        const bonus = this.salary * multiplier;

        return bonus
    }
}

module.exports = Employee;

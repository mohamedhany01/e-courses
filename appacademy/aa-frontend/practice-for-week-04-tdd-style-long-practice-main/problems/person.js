class Person {

  constructor(name, age) {
    this.name = name;
    this.age = age;
  }

  sayHello() {
    return `Hello! I am ${this.name}`;
  }

  visit(otherPerson) {
    return `${this.name} visited ${otherPerson.name}`;
  }

  switchVisit(otherPerson) {
    return otherPerson.visit(this);
  }

  update(obj) {
    if (typeof obj !== 'object' || obj === null || !Object.keys(obj).length) {
      throw new TypeError("Must be an object");
    }

    if (!obj.name || !obj.age) {
      throw new TypeError("Must be an object with name and age");
    }

    this.name = obj.name;
    this.age = obj.age;
  }

  tryUpdate(obj) {

    try {
      this.update(obj);

      return true;
    } catch (error) {

      return false;
    }
  }

  static greetAll(obj) {

    if (!Array.isArray(obj)) {
      return null;
    }

    let result = [];

    if (!obj) {
      return result;
    }

    result = obj.map(o => o.sayHello());

    return result;
  }
}

module.exports = Person;
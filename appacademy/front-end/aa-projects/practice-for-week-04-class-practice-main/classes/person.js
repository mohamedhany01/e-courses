// Your code here
class Person {
  constructor(firstName, lastName, age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  introduce() {
    console.log(`Hi, I'm ${this.firstName} ${this.lastName}, and I'm ${this.age} years old`);
  }

  static introducePeople(persons) {
    if (!Array.isArray(persons)) {
      throw new Error("introducePeople only takes an array as an argument.");
    }

    for (let i = 0; i < persons.length; i++) {
      const element = persons[i];
      if (!(element instanceof Person)) {
        throw new Error("All items in array must be Person class instances.");
      }
    }

    for (let i = 0; i < persons.length; i++) {
      const element = persons[i];
      element.introduce();
    }

  }
}

/****************************************************************************/
/******************* DO NOT EDIT CODE BELOW THIS LINE ***********************/

try {
  module.exports = Person;
} catch {
  module.exports = null;
}
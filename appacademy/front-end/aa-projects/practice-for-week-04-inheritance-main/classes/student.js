const Person = require('./person');

class Student extends Person {
  constructor(firstName, lastName, major, GPA) {
    super(firstName, lastName);
    this.major = major;
    this.GPA = GPA;
  }

  static compareGPA(s1, s2) {
    if (s1.GPA > s2.GPA) {
      return `${s1.firstName} ${s1.lastName} has the higher GPA.`;
    } else if (s2.GPA > s1.GPA) {
      return `${s2.firstName} ${s2.lastName} has the higher GPA.`;
    } else {
      return "Both students have the same GPA"
    }
  }
}


/****************************************************************************/
/******************* DO NOT EDIT CODE BELOW THIS LINE ***********************/

try {
  module.exports = Student;
} catch {
  module.exports = null;
}
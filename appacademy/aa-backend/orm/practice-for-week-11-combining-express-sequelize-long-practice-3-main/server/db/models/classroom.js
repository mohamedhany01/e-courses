'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Classroom extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Classroom.hasMany(models.StudentClassroom);
      Classroom.belongsToMany(
          models.Student, {
            through: models.StudentClassroom,
            foreignKey: 'classroomId',
            otherKey: 'studentId'
          }
      );
      Classroom.hasMany(
        models.Supply, {
          foreignKey: 'classroomId',
          onDelete: 'CASCADE',
          hooks: true
      });
    }
  };
  Classroom.init({
    name: DataTypes.STRING,
    studentLimit: {
      type: DataTypes.INTEGER,
      validate: {
        min: 1,
      }
    },
    // Phase 5: Alternate solution 3
    // Your code here
  }, {
    sequelize,
    modelName: 'Classroom',
  });
  return Classroom;
};
'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Color extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  Color.init({
    name: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
    isPrimary: {
      type: DataTypes.BOOLEAN,
      allowNull: false,
      unique: true,
      defaultValue: false,
      validate: {
        shouldBeTheseOnly() {
          const validColors = ["red", "blue", "yellow"];
          const currentColor = this.name.toLowerCase();

          if (validColors.includes(currentColor)) {
            this.isPrimary = true;
          }
          else {
            this.isPrimary = false;
          }
        }
      }
    },
  }, {
    sequelize,
    modelName: 'Color',
  });
  return Color;
};
'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Musician extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      Musician.belongsTo(models.Band, {
        foreignKey: 'bandId',
        onDelete: 'cascade',
      });

      Musician.belongsToMany(models.Instrument, {
        through: models.MusicianInstrument,
        onDelete: 'cascade',
      });
    }
  };
  Musician.init({
    firstName: DataTypes.STRING,
    lastName: DataTypes.STRING,
    bandId: {
      allowNull: false,
      type: DataTypes.INTEGER,
      references: {
        model: "Bands",
        key: "id"
      },
      onDelete: "cascade"
    }
  }, {
    sequelize,
    modelName: 'Musician',
  });
  return Musician;
};

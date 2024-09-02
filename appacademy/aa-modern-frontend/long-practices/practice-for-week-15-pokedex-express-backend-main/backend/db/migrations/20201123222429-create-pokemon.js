'use strict';

const { types } = require("../models/pokemonType");

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('Pokemons', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      number: {
        allowNull: false,
        type: Sequelize.INTEGER,
        unique: true,
      },
      attack: {
        allowNull: false,
        type: Sequelize.INTEGER
      },
      defense: {
        allowNull: false,
        type: Sequelize.INTEGER
      },
      imageUrl: {
        allowNull: false,
        type: Sequelize.STRING(255),
      },
      name: {
        allowNull: false,
        type: Sequelize.STRING(255),
        unique: true,
      },
      type: {
        allowNull: false,
        type: Sequelize.ENUM(types),
      },
      moves: {
        allowNull: false,
        type: Sequelize.ARRAY(Sequelize.STRING(30)),
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
        defaultValue: Sequelize.fn("now"),
      },
      encounterRate: {
        allowNull: false,
        type: Sequelize.DECIMAL(3, 2),
        defaultValue: 1.00,
      },
      catchRate: {
        allowNull: false,
        type: Sequelize.DECIMAL(3, 2),
        defaultValue: 1.00,
      },
      captured: {
        allowNull: false,
        type: Sequelize.BOOLEAN,
        defaultValue: false,
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
        defaultValue: Sequelize.fn("now"),
      }
    });
  },
  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable('Pokemons');
  }
};

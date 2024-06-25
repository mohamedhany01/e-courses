'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addConstraint("Colors", {
      fields: ['name'],
      type: 'unique',
      name: "add_unique_name_colors"
    });
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.removeConstraint("Colors", "add_unique_name_colors");
  }
};

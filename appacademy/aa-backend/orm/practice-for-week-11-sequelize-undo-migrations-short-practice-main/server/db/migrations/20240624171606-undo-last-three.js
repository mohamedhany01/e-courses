'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.dropTable('cats');
    await queryInterface.dropTable('users');
    await queryInterface.dropTable('games');
  }
};

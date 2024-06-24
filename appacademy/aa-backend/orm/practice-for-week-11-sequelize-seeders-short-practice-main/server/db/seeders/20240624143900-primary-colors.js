'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert('Colors', [
      {
        name: 'red',
      },
      {
        name: 'blue',
      },
      {
        name: 'yellow',
      },
    ], {});
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete('Colors', { name: ['red', 'blue', 'yellow'] }, {});
  }
};

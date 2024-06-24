'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.bulkInsert('Colors', [
      {
        name: 'orange',
        createdAt: new Date('2014'),
        updatedAt: new Date('2016'),
      },
      {
        name: 'purple',
        createdAt: new Date('2017'),
        updatedAt: new Date('2018'),
      },
      {
        name: 'black',
        createdAt: new Date('2020'),
        updatedAt: new Date('2024'),
      },
    ], {});
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete('Colors', { name: ['orange', 'purple', 'black'] }, {});
  }
};

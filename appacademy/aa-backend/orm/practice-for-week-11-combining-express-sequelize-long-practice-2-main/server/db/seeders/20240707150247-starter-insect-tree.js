'use strict';

const { Tree, Insect } = require("../models");

const seedData = [
  {
    insect: { name: "Western Pygmy Blue Butterfly" },
    trees: [
      { tree: "General Sherman" },
      { tree: "General Grant" },
      { tree: "Lincoln" },
      { tree: "Stagg" },
    ],
  },
  {
    insect: { name: "Patu Digua Spider" },
    trees: [
      { tree: "Stagg" }
    ],
  },
];


/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {

    for (let i = 0; i < seedData.length; i++) {
      
      const { insect, trees } = seedData[i];

      const insectResult = await Insect.findOne({ where: insect });

      for (let j = 0; j < trees.length; j++) {
        const tree = trees[j];

        const treeResult = await Tree.findOne({ where: tree });

        await insectResult.addTree(treeResult);
      }

    }
  },

  async down(queryInterface, Sequelize) {

    for (let i = 0; i < insectTress.length; i++) {

      const { insect, trees } = insectTress[i];

      const insectResult = await Insect.findOne({ where: insect });

      for (let j = 0; j < trees.length; j++) {
        const tree = trees[j];

        const treeResult = await Tree.findOne({ where: tree });

        await insectResult.removeTree(treeResult);
      }
    }
  }
};

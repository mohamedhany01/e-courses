'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.bulkInsert('Insects', [
      {
        name: 'Western Pygmy Blue Butterfly',
        description: 'The smallest member of the butterfly family, recognized by copper brown and dull blue pattern at the bases of both wings.',
        territory: 'North America, Hawaii, Middle East',
        fact: 'Butterflies have been around for more than 200 million years, predating even pollen-rich flowers.',
        millimeters: 12,
      },
      {
        name: 'Patu Digua Spider',
        description: 'One of the smallest known spiders, with males growing to only about a third of a millimeter in size.',
        territory: 'Rio Digua river, El Queremal, Valle del Cauca region, Colombia',
        fact: 'Male spiders are generally smaller than females. Some spiders, like the female Anapistula caecula of West Africa, may be even smaller.',
        millimeters: 0.33,
      },
      {
        name: 'Scarlet Dwarf Dragonfly',
        description: 'The smallest known dragonfly species, also known as the northern pygmyfly or tiny dragonfly. Part of the Libellulidae family.',
        territory: 'Southeast Asia, China, Japan, occasionally Australia',
        fact: 'While this is the smallest dragonfly, prehistoric dragonflies like Meganeura had wingspans exceeding 70 centimeters and lived 300 million years ago.',
        millimeters: 20,
      },
      {
        name: 'Pygmy Sorrel Moth',
        description: 'One of the smallest moths from the Nepticulidae family, also known as pigmy moths or midget moths.',
        territory: 'Various (not specified in the text)',
        fact: 'As larvae, they mine leaves of host plants, leaving unique and large imprints on the leaves they feed on.',
        millimeters: 3,
      },
      {
        name: 'Bolbe Pygmaea Mantis',
        description: 'The smallest known mantis species, found in Australia.',
        territory: 'Australia',
        fact: 'Mantises have inspired martial arts techniques and are sometimes kept as pets. They have been revered in various cultures throughout history.',
        millimeters: 10,
      },
      {
        name: 'Microtityus Minimus Scorpion',
        description: 'The world\'s tiniest scorpion, discovered in 2014 in the Dominican Republic.',
        territory: 'Greater Antillean Island of Hispaniola, Dominican Republic',
        fact: 'Scorpions have evolved over 430 million years, developing features like venomous stingers and strong exoskeletons.',
        millimeters: 11,
      },
      {
        name: 'Euryplatea Nanaknihali Fly',
        description: 'The smallest fly species on earth, less than half a millimeter in size.',
        territory: 'Not specified in the text',
        fact: 'These tiny flies lay eggs inside ant heads. The larvae eventually decapitate the host ant as they grow.',
        millimeters: 0.5,
      },
      {
        name: 'Uranotaenia Lowii Mosquito',
        description: 'One of the smallest mosquito species, also known as the pale-footed Uranotaenia.',
        territory: 'Southern United States, from Texas to Florida, north to North Carolina',
        fact: 'Unlike most mosquitoes, this species prefers to bite frogs and amphibians rather than humans.',
        millimeters: 2.5,
      },
      {
        name: 'Dicopomorpha Echmepterygis',
        description: 'The smallest known insect species, belonging to the fairyfly or fairy wasp family.',
        territory: 'Worldwide',
        fact: 'They have no wings or eyes, just holes for mouths and two tiny antennae.',
        millimeters: 0.139,
      },
      {
        name: 'Kikiki Huna',
        description: 'The smallest known flying insect, a species of fairyfly.',
        territory: 'Hawaii, Costa Rica, Trinidad',
        fact: 'Named kikiki, which means "tiny bit" in Hawaiian, this insect is closely related to the tinkerbella nana wasp.',
        millimeters: 0.15,
      }
    ], {});
  },

  down: async (queryInterface, Sequelize) => {

    await queryInterface.bulkDelete('Insects', {
      name: [
        'Western Pygmy Blue Butterfly',
        'Patu Digua Spider',
        'Scarlet Dwarf Dragonfly',
        'Pygmy Sorrel Moth',
        'Bolbe Pygmaea Mantis',
        'Microtityus Minimus Scorpion',
        'Euryplatea Nanaknihali Fly',
        'Uranotaenia Lowii Mosquito',
        'Dicopomorpha Echmepterygis',
        'Kikiki Huna'
      ]
    }, {});
  }
};

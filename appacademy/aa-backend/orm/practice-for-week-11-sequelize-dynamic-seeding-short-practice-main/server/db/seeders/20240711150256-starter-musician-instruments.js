'use strict';

const {
  Musician, Instrument
} = require('../models');

const musicianInstruments = [
  {
    musician: { firstName: 'Adam', lastName: 'Appleby' },
    instruments: [{ type: 'piano' }, { type: 'guitar' }]
  },
  {
    musician: { firstName: 'Anton', lastName: 'Martinovic' },
    instruments: [{ type: 'piano' }, { type: 'bass' }]
  },
  {
    musician: { firstName: 'Wilson', lastName: 'Holt' },
    instruments: [{ type: 'cello' }]
  },
  {
    musician: { firstName: 'Marine', lastName: 'Sweet' },
    instruments: [{ type: 'saxophone' }]
  },
  {
    musician: { firstName: 'Georgette', lastName: 'Kubo' },
    instruments: [{ type: 'drums' }, { type: 'trumpet' }, { type: 'saxophone' }]
  },
  {
    musician: { firstName: 'Aurora', lastName: 'Hase' },
    instruments: [{ type: 'violin' }, { type: 'cello' }]
  },
  {
    musician: { firstName: 'Trenton', lastName: 'Lesley' },
    instruments: [{ type: 'piano' }]
  },
  {
    musician: { firstName: 'Camila', lastName: 'Nenci' },
    instruments: [{ type: 'piano' }]
  },
  {
    musician: { firstName: 'Rosemarie', lastName: 'Affini' },
    instruments: [{ type: 'piano' }, { type: 'violin' }]
  },
  {
    musician: { firstName: 'Victoria', lastName: 'Cremonesi' },
    instruments: [{ type: 'violin' }]
  },
];

module.exports = {
  up: async (queryInterface, Sequelize) => {
    for (let i = 0; i < musicianInstruments.length; i++) {
      const { musician, instruments } = musicianInstruments[i];

      const currentMusician = await Musician.findOne({ where: { ...musician } });

      for (let j = 0; j < instruments.length; j++) {
        const currentInstrument = instruments[j];

        const instrumentId = await Instrument.findOne({ where: { ...currentInstrument } });

        await currentMusician.addInstruments(instrumentId);

      }
    }

    // OR
    
    // for (let musicianIdx = 0; musicianIdx < musicianInstruments.length; musicianIdx++) {
    //   const { musician, instruments } = musicianInstruments[musicianIdx];
    //   const currentMusician = await Musician.findOne({ where: { ...musician } });
    //   const currentInstruments = await Instrument.findAll({ where: { [Op.or]: instruments } });

    //   await currentMusician.addInstruments(currentInstruments);
    // }
  },

  down: async (queryInterface, Sequelize) => {
    for (let i = 0; i < musicianInstruments.length; i++) {
      const { musician, instruments } = musicianInstruments[i];

      const currentMusician = await Musician.findOne({ where: { ...musician } });

      for (let j = 0; j < instruments.length; j++) {
        const currentInstrument = instruments[j];

        const instrumentId = await Instrument.findOne({ where: { ...currentInstrument } });

        await currentMusician.removeInstruments(instrumentId);

      }
    }

    // OR

    // for (let musicianIdx = 0; musicianIdx < musicianInstruments.length; musicianIdx++) {
    //   const { musician, instruments } = musicianInstruments[musicianIdx];
    //   const currentMusician = await Musician.findOne({ where: { ...musician } });
    //   const currentInstruments = await Instrument.findAll({ where: { [Op.or]: instruments } });

    //   await currentMusician.removeInstruments(currentInstruments);
    // }
  }
};

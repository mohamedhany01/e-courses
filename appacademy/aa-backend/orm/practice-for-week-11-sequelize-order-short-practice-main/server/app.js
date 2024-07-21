// Instantiate Express and the application - DO NOT MODIFY
const express = require('express');
const app = express();

// Import environment variables in order to connect to database - DO NOT MODIFY
require('dotenv').config();
require('express-async-errors');

// Import the models used in these routes - DO NOT MODIFY
const { Band, Musician } = require('./db/models');

// Express using json - DO NOT MODIFY
app.use(express.json());


// STEP 1: Order by one attribute
// Get all bands, ordered by createdAt, latest first
app.get('/bands/latest', async (req, res, next) => {
    const bands = await Band.findAll({
        attributes: ['name'],
        order: [
            ['createdAt', 'DESC'],
        ]
    });
    res.json(bands);
})

// STEP 2: Order by multiple attributes
// Get all musicians, ordered by last name, then first name, alphabetically
app.get('/musicians/alphabetic', async (req, res, next) => {
    const musicians = await Musician.findAll({
        attributes: ['firstName', 'lastName'],
        order: [
            ['lastName'],
            ['firstName']
        ]
    });
    res.json(musicians);
})

// STEP 3: Order by multiple attributes, including nested attributes
// Get bands and associated musicians, ordered by band name, then musician last 
// name, then first name, alphabetically
app.get('/bands/alphabetic-musicians', async (req, res, next) => {
    const bands = await Band.findAll({
        attributes: ['name'],
        include: {
            model: Musician,
            attributes: ['firstName', 'lastName'],
        },
        order: [
            ['name'],
            [Musician, 'lastName'],
            [Musician, 'firstName']
        ]
    })
    res.json(bands);
})


// Root route - DO NOT MODIFY
app.get('/', (req, res) => {
    res.json({
        message: "API server is running"
    });
});

// Set port and listen for incoming requests - DO NOT MODIFY
const port = 5000;
app.listen(port, () => console.log('Server is listening on port', port));
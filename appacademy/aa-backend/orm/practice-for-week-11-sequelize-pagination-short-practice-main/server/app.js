// Instantiate Express and the application - DO NOT MODIFY
const express = require('express');
const app = express();

// Import environment variables in order to connect to database - DO NOT MODIFY
require('dotenv').config();
require('express-async-errors');

// Import the models used in these routes - DO NOT MODIFY
const { Musician, Band, Instrument } = require('./db/models');

// Express using json - DO NOT MODIFY
app.use(express.json());
app.use(pagination);


app.get('/musicians', async (req, res, next) => {
    // Parse the query params, set default values, and create appropriate
    // offset and limit values if necessary.
    const { size, offset } = req.pagination;

    // Query for all musicians
    // Include attributes for `id`, `firstName`, and `lastName`
    // Include associated bands and their `id` and `name`
    // Order by musician `lastName` then `firstName`
    const musicians = await Musician.findAll({
        order: [['lastName'], ['firstName']],
        attributes: ['id', 'firstName', 'lastName'],
        include: [{
            model: Band,
            attributes: ['id', 'name']
        }],
        // add limit key-value to query
        // add offset key-value to query
        offset,
        limit: size
    });


    res.json(musicians)
});


// BONUS: Pagination with bands
app.get('/bands', async (req, res, next) => {
    // Parse the query params, set default values, and create appropriate
    // offset and limit values if necessary.
    const { size, offset } = req.pagination;
    
    // Query for all bands
    // Include attributes for `id` and `name`
    // Include associated musicians and their `id`, `firstName`, and `lastName`
    // Order by band `name` then musician `lastName`
    const bands = await Band.findAll({ 
        order: [['name'], [Musician, 'lastName']], 
        attributes: ['id', 'name'],
        include: [{
            model: Musician,
            attributes: ['id', 'firstName', 'lastName']
        }],
        // add limit key-value to query
        // add offset key-value to query
        offset,
        limit: size
    });

    res.json(bands)
});


// BONUS: Pagination with instruments
app.get('/instruments', async (req, res, next) => {
    // Parse the query params, set default values, and create appropriate
    // offset and limit values if necessary.
    const { size, offset } = req.pagination;
    
    // Query for all instruments
    // Include attributes for `id` and `type`
    // Include associated musicians and their `id`, `firstName` and `lastName`
    // Omit the MusicianInstruments join table attributes from the results
    // Include each musician's associated band and their `id` and `name`
    // Order by instrument `type`, then band `name`, then musician `lastName`
    const instruments = await Instrument.findAll({ 
        order: [['type'], [Musician, Band, 'name'], [Musician, 'lastName']], 
        attributes: ['id', 'type'],
        include: [{
            model: Musician,
            attributes: ['id', 'firstName', 'lastName'],
            // Omit the join table (MusicianInstruments) attributes
            through: { attributes: [] },
            include: [{
                model: Band,
                attributes: ['id', 'name']
            }]
        }],
        // add limit key-value to query
        // add offset key-value to query
        offset,
        limit: size
    });

    res.json(instruments)
});

// ADVANCED BONUS: Reduce Pagination Repetition

function pagination(req, res, next) {

    let { size, page } = req.query;

    if (!size && !page) {
        req.pagination = {
            offset: 0,
            size: 5
        }

        return next();
    }

    size = parseInt(size);
    page = parseInt(page);

    if (size === 0 || page === 0) {

        req.pagination = {
            offset: -1,
            size: -1
        }

        return next();
    }

    size = size ? size : 5;
    page = page ? page : -1;

    req.pagination = {
        offset: size * (page - 1),
        size: size
    }

    return next();
}


// Using method
function getPagination({ size, page }) {

    if (!size && !page) {
        return {
            offset: 0,
            size: 5
        }
    }

    size = parseInt(size);
    page = parseInt(page);

    if (size === 0 || page === 0) {
        return {
            offset: -1,
            size: -1
        }
    }

    size = size ? size : 5;
    page = page ? page : 1;

    return {
        offset: size * (page - 1),
        size: size
    }
}


// Root route - DO NOT MODIFY
app.get('/', (req, res) => {
    res.json({
        message: "API server is running"
    });
});

// Set port and listen for incoming requests - DO NOT MODIFY
const port = 5000;
app.listen(port, () => console.log('Server is listening on port', port));
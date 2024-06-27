// Instantiate Express and the application - DO NOT MODIFY
const express = require('express');
const app = express();

// Error handling, env variables, and json middleware - DO NOT MODIFY
require('express-async-errors');
require('dotenv').config();
app.use(express.json());

// Import the models used in these routes - DO NOT MODIFY
const { Puppy } = require('./db/models');

// Index of all puppies - DO NOT MODIFY
app.get('/puppies', async (req, res, next) => {
    const allPuppies = await Puppy.findAll({ order: [['name', 'ASC']] });

    res.json(allPuppies);
});


// STEP 1: Update a puppy by id
app.put('/puppies/:puppyId', async (req, res, next) => {
    const { puppyId } = req.params;

    const { name, ageYrs, breed, weightLbs, microchipped } = req.body;

    const puppy = await Puppy.findByPk(puppyId);

    if (!puppy) {
        res.status(404);
        return res.json({ message: 'Puppy not found' });
    }

    await Puppy.update(
        {
            name: name || puppy.name,
            ageYrs: ageYrs || puppy.ageYrs,
            breed: breed || puppy.breed,
            weightLbs: weightLbs || puppy.weightLbs,
            microchipped: microchipped || puppy.microchipped
        }, { where: { id: puppyId } }
    );

    const updatedPuppy = await Puppy.findByPk(puppyId);

    res.json({
        message: `Successfully updated puppy with id ${puppyId}.`,
        puppy: updatedPuppy
    });
})


// STEP 2: Delete a puppy by id
app.delete('/puppies/:puppyId', async (req, res, next) => {
    const { puppyId } = req.params;

    const puppy = await Puppy.findByPk(puppyId);

    if (!puppy) {
        res.status(404);
        return res.json({ message: 'Puppy not found' });
    }

    await puppy.destroy();

    res.json({
        message: `Successfully deleted puppy with id ${puppyId}.`,
        puppy: puppy
    });
})


// Root route - DO NOT MODIFY
app.get('/', (req, res) => {
    res.json({
        message: "API server is running"
    });
});

// Set port and listen for incoming requests - DO NOT MODIFY
if (require.main === module) {
    const port = 8000;
    app.listen(port, () => console.log('Server is listening on port', port));
} else {
    module.exports = app;
}
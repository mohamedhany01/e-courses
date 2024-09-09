
const express = require('express');

// Create the Express router.

const router = express.Router();

// Define sub-routes.

router.get('/bio', (req, res) => {
  res.send('Bio');
});

router.get('/contact', (req, res) => {
  res.send('Contact');
});

// Define catch-all.

router.get('*', (req, res) => {
  res.send(`Links: <a href="${req.baseUrl}/bio">Bio</a> | <a href="${req.baseUrl}/contact">Contact</a>`);
});

// Export the module so it can be imported in the application.

module.exports = router;
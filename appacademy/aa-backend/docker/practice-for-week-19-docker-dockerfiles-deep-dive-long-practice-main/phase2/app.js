
const express = require('express');
const routes = require('./routes');

// Create the Express app.
const app = express();

// Set the pug view engine.
app.set('view engine', 'pug');

// Define routes.

app.get('/', (req, res) => {
  let links = 'Links<ul>';
  links += '<li><a href="/somewhere">Somewhere</a></li>';
  links += '<li><a href="/nowhere">Nowhere</a></li>';
  links += '<li><a href="/capital-letters/abc">Capital Letters</a></li>';
  links += '<li><a href="/margot">Margot</a></li>';
  links += '<li><a href="/margeaux">Margeaux</a></li>';
  links += '<li><a href="/...xyz">The End</a></li>';
  links += '</ul>';
  res.send('<h1>Hello from Express!</h1>' + links);
});

app.get('/*xyz', (req, res) => {
  res.send('That\'s all I wrote.');
});

app.get('/capital-letters/:letters', (req, res) => {
  res.send(req.params.letters.toUpperCase());
});

app.use('/margot', routes);
app.use('/margeaux', routes);

app.all(/^\/[A-Za-z0-9\-_]*$/, (req, res) => {
  const randomNumber = Math.floor(Math.random() * 100);

  res.render('index',
    { method: req.method, path: req.path, randomNumber });
});

// Define a port and start listening for connections.

const port = 8081;
app.listen(port, () => console.log(`Listening on port ${port}...`));
const express = require('express');
const app = express();

// All files under http://localhost:5000/assets
// app.use(express.static('assets'));

// http://localhost:5000/hello-world.js
// app.use(express.static('assets/scripts'));

// Virtual path "stylesheets"
// http://localhost:5000/stylesheets/application.css
// app.use("/stylesheets", express.static('assets/css'));

// Virtual path "stylesheets"
// http://localhost:5000/stylesheets/application.css
app.use("/stickers", express.static('assets/images'));

const port = 5000;
app.listen(port, () => console.log('Server is listening on port', port));

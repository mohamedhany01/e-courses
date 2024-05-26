const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.send('GET / This is the root URL');
});

app.get("/some-generic-error", (req, res) => {
  throw new Error("Generic error");
});

// "Resource Not Found" Middleware
app.use((req, res, next) => {
  const error = new Error("Sorry, the requested resource couldn't be found.");
  error.statusCode = 404;
  next(error);
});

// Catch-all Error Handling Middleware
app.use((err, req, res, next) => {
  // console.error(err.stack);
  res.status(err.statusCode || 500).json({
    message: err.message,
    statusCode: err.statusCode || 500,
  });
});

const port = 5000;
app.listen(port, () => console.log('Server is listening on port', port));

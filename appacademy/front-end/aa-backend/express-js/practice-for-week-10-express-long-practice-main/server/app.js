const express = require('express');

require('express-async-errors');

const app = express();

require('dotenv').config();

console.log(process.env.NODE_ENV);

const dogRouter = require("./routes/dogs");

const logRequestInfo = (req, res, next) => {
  res.on('finish', () => {
    const { method, url } = req;
    const { statusCode } = res;

    console.log(`Method: ${method}, URL: ${url}, status code: ${statusCode}`);
  });

  next();
}

app.use('/static', express.static('assets'));

app.use(logRequestInfo);

// Middleware to parse JSON bodies
app.use(express.json());

app.use("/dogs", dogRouter);

// For testing purposes, GET /
app.get('/', (req, res) => {
  res.json("Express server running. No content provided at root level. Please use another route.");
});

// For testing express.json middleware
app.post('/test-json', (req, res, next) => {
  // send the body as JSON with a Content-Type header of "application/json"
  // finishes the response, res.end()
  res.json(req.body);
  next();
});

// For testing express-async-errors
app.get('/test-error', async (req, res) => {
  throw new Error("Hello World!")
});

// "Resource Not Found" Middleware
app.use((req, res, next) => {
  const error = new Error("The requested resource couldn't be found.");
  error.statusCode = 404;
  next(error);
});

// Catch-all Error Handling Middleware
app.use((err, req, res, next) => {
  // console.error(err.stack);
  if (process.env.NODE_ENV !== "production") {
    next(err);
  } else {
    res.status(err.statusCode || 500).json({
      message: err.message,
      statusCode: err.statusCode || 500,
    });
  }
});

const port = 5000;
app.listen(port, () => console.log('Server is listening on port', port));

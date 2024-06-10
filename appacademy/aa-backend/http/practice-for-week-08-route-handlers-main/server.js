const http = require('http');
const fs = require('fs');

let nextDogId = 1;

function getNewDogId() {
  const newDogId = nextDogId;
  nextDogId++;
  return newDogId;
}

const dogsDB = new Map();

function processRoute(url, method, res, req) {
  let data = "";

  const paths = url.split('/');
  const isDogID = typeof (parseInt(paths[2], 10)) === 'number';
  const isNewDog = paths[2] === "new" && typeof (paths[2]) === 'string';


  if (method === 'GET') {

    if (url === "/") {
      res.statusCode = 200;
      res.setHeader('Content-Type', 'text/plain');

      data = 'Dog Club';
      return data;
    }

    if (paths.length === 2 && paths[1] === "dogs") {
      res.statusCode = 200;
      res.setHeader('Content-Type', 'text/plain');

      data = 'Dogs index';
      return data;
    }

    if (paths.length === 3 && isDogID) {
      const id = parseInt(paths[2], 10);
      if (dogsDB.has(id)) {
        data = dogsDB.get(id);

        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        return data.name;
      }
    }

    if (paths.length === 3 && isNewDog) {
      const addForm = fs.readFileSync('./index.html', 'utf-8');

      res.statusCode = 200;
      res.setHeader('Content-Type', 'text/html');
      return addForm;
    }

    if (paths.length === 4 && isDogID && paths[3] === "edit") {
      const id = parseInt(paths[2], 10);
      if (dogsDB.has(id)) {
        data = dogsDB.get(id);

        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        data = `Dog edit form page for dogId ${id}`;

        return data;
      }
    }
  }

  if (method === 'POST') {
    if (paths.length === 3 && isDogID) {
      const id = parseInt(paths[2], 10);
      if (dogsDB.has(id)) {
        data = dogsDB.get(id);

        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        res.setHeader('Location', `/dogs/${newID}`);

        data = "NONE";
        return data;
      }
    }

    if (paths.length === 2 && paths[1] === "dogs") {
      const newID = getNewDogId();
      const dogName = req.body.name;

      dogsDB.set(newID, {
        id: newID,
        name: dogName
      });

      res.statusCode = 302;
      res.setHeader('Content-Type', 'text/plain');
      res.setHeader('Location', `/dogs/${newID}`);

      data = "NONE";
      return data;
    }
  }

  return data;
}

const server = http.createServer((req, res) => {
  console.log(`${req.method} ${req.url}`);

  let reqBody = "";
  req.on("data", (data) => {
    reqBody += data;
  });

  // When the request is finished processing the entire body
  req.on("end", () => {
    // Parsing the body of the request
    if (reqBody) {
      req.body = reqBody
        .split("&")
        .map((keyValuePair) => keyValuePair.split("="))
        .map(([key, value]) => [key, value.replace(/\+/g, " ")])
        .map(([key, value]) => [key, decodeURIComponent(value)])
        .reduce((acc, [key, value]) => {
          acc[key] = value;
          return acc;
        }, {});
      console.log(req.body);
    }
    // Do not edit above this line

    const { url, method } = req;

    const result = processRoute(url, method, res, req);

    if (result) {
      return res.end(result);
    }

    // Do not edit below this line
    // Return a 404 response when there is no matching route handler
    res.statusCode = 404;
    res.setHeader('Content-Type', 'text/plain');
    return res.end('No matching route handler found for this endpoint');
  });
});

const port = 5000;

server.listen(port, () => console.log('Server is listening on port', port));
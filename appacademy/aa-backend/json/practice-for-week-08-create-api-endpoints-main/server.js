const http = require('http');

let dogs = [
  {
    dogId: 1,
    name: "Fluffy",
    age: 2
  }
];

let nextDogId = 2;

function getNewDogId() {
  const newDogId = nextDogId;
  nextDogId++;
  return newDogId;
}

const server = http.createServer((req, res) => {
  console.log(`${req.method} ${req.url}`);

  // assemble the request body
  let reqBody = "";
  req.on("data", (data) => {
    reqBody += data;
  });

  req.on("end", () => { // request is finished assembly the entire request body
    // Parsing the body of the request depending on the Content-Type header
    if (reqBody) {
      switch (req.headers['content-type']) {
        case "application/json":
          req.body = JSON.parse(reqBody);
          break;
        case "application/x-www-form-urlencoded":
          req.body = reqBody
            .split("&")
            .map((keyValuePair) => keyValuePair.split("="))
            .map(([key, value]) => [key, value.replace(/\+/g, " ")])
            .map(([key, value]) => [key, decodeURIComponent(value)])
            .reduce((acc, [key, value]) => {
              acc[key] = value;
              return acc;
            }, {});
          break;
        default:
          break;
      }
      console.log(req.body);
    }

    /* ======================== ROUTE HANDLERS ======================== */

    // GET /dogs
    if (req.method === 'GET' && req.url === '/dogs') {

      res.statusCode = 200;
      res.setHeader("Content-Type", "application/json");
      return res.end(JSON.stringify(dogs));
    }

    // GET /dogs/:dogId
    if (req.method === 'GET' && req.url.startsWith('/dogs/')) {
      const urlParts = req.url.split('/'); // ['', 'dogs', '1']
      let responseData;

      if (urlParts.length === 3) {
        const dogId = parseInt(urlParts[2]);
        responseData = dogs.filter(dog => dog.dogId === dogId);

        if (responseData.length > 0) {
          res.statusCode = 200;
          res.setHeader("Content-Type", "application/json");
          responseData = JSON.stringify(responseData[0]);
        } else {
          responseData = "No dog with this ID";
          res.statusCode = 404;
          res.setHeader("Content-Type", "text/html");
        }
      }

      return res.end(responseData);
    }

    // POST /dogs
    if (req.method === 'POST' && req.url === '/dogs') {
      const { name, age } = req.body;

      const newDog = {
        dogId: getNewDogId(),
        name: name,
        age: age
      };

      dogs.push(newDog);

      res.statusCode = 201;
      res.setHeader("Content-Type", "application/json");

      return res.end(JSON.stringify(newDog));
    }

    // PUT or PATCH /dogs/:dogId
    if ((req.method === 'PUT' || req.method === 'PATCH') && req.url.startsWith('/dogs/')) {
      const urlParts = req.url.split('/');

      let responseData;

      if (urlParts.length === 3) {

        const dogId = parseInt(urlParts[2]);
        const { name, age } = req.body;

        const dogIndex = dogs.findIndex(dog => dog.dogId === dogId);

        if (dogIndex === -1) {
          responseData = "No dog with this ID";

          res.statusCode = 404;
          res.setHeader("Content-Type", "text/html");
        } else {
          dogs[dogIndex].age = age;
          dogs[dogIndex].name = name;

          res.statusCode = 200;
          res.setHeader("Content-Type", "application/json");
          responseData = JSON.stringify(dogs[dogIndex]);
        }
      }

      return res.end(responseData);
    }

    // DELETE /dogs/:dogId
    if (req.method === 'DELETE' && req.url.startsWith('/dogs/')) {

      const urlParts = req.url.split('/');

      let responseData;

      if (urlParts.length === 3) {

        const dogId = parseInt(urlParts[2]);

        const dogIndex = dogs.findIndex(dog => dog.dogId === dogId);

        if (dogIndex === -1) {
          responseData = "No dog with this ID";

          res.statusCode = 404;
          res.setHeader("Content-Type", "text/html");
        } else {
          dogs = dogs.filter(dog => dog.dogId !== dogId)

          res.statusCode = 200;
          res.setHeader("Content-Type", "application/json");
          responseData = JSON.stringify({
            message: "Successfully deleted"
          });
        }
      }

      return res.end(responseData);
    }

    // No matching endpoint
    res.statusCode = 404;
    res.setHeader('Content-Type', 'application/json');
    return res.end('Endpoint not found');
  });

});


if (require.main === module) {
  const port = 8000;
  server.listen(port, () => console.log('Server is listening on port', port));
} else {
  module.exports = server;
}

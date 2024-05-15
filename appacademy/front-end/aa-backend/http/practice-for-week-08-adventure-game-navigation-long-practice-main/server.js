const http = require('http');
const fs = require('fs');

const { Player } = require('./game/class/player');
const { World } = require('./game/class/world');

const worldData = require('./game/data/basic-world-data');

let player;
let world = new World();
world.loadWorld(worldData);

const server = http.createServer((req, res) => {

  /* ============== ASSEMBLE THE REQUEST BODY AS A STRING =============== */
  let reqBody = '';
  req.on('data', (data) => {
    reqBody += data;
  });

  req.on('end', () => { // After the assembly of the request body is finished
    /* ==================== PARSE THE REQUEST BODY ====================== */
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
    }

    /* ======================== ROUTE HANDLERS ========================== */
    // Phase 1: GET
    if (req.method === "GET" && req.url === "/") {

      const indexPage = fs.readFileSync("views/new-player.html", "utf-8");
      const rooms = world.availableRoomsToString();

      const responseBody = indexPage.replace(/#{availableRooms}/g, rooms);

      res.statusCode = 200;
      res.setHeader("Content-Type", "text/html");
      return res.end(responseBody);
    }

    // Phase 2: POST /player
    if (req.method === "POST" && req.url === "/player") {

      const { name, roomId } = req.body;
      const currentRoom = world.rooms[roomId];

      player = new Player(name, currentRoom);

      console.log(req.url);
      console.log(player);

      res.statusCode = 302;
      res.setHeader("Location", `/rooms/${roomId}`);

      return res.end();
    }

    // Phase 3: GET /rooms/:roomId
    if (req.method === "GET" && req.url.startsWith("/rooms/")) {

      console.log(req.url);
      console.log(player);

      const urlPaths = req.url.split("/");

      if (urlPaths.length === 3) {

        if (!player) {

          res.statusCode = 302;
          res.setHeader("Location", "/");

          return res.end();
        }

        const roomID = parseInt(urlPaths[2]);
        const currentRoom = world.rooms[roomID];

        if (roomID !== player.currentRoom.id) {

          res.statusCode = 302;
          res.setHeader("Location", `/rooms/${player.currentRoom.id}`);

          return res.end();
        }

        const roomName = currentRoom.name;
        const inventory = player.inventoryToString();
        const items = currentRoom.itemsToString();
        const exit = currentRoom.exitsToString();

        const indexPage = fs.readFileSync("views/room.html", "utf-8");

        let responseBody = indexPage.replace(/#{roomName}/g, roomName);
        responseBody = responseBody.replace(/#{inventory}/g, inventory);
        responseBody = responseBody.replace(/#{roomItems}/g, items);
        responseBody = responseBody.replace(/#{exits}/g, exit);

        res.statusCode = 200;
        res.setHeader("Content-Type", "text/html");

        return res.end(responseBody);
      }
    }

    // Phase 4: GET /rooms/:roomId/:direction
    if (req.method === "GET" && req.url.startsWith("/rooms/")) {

      const urlPaths = req.url.split("/");

      if (urlPaths.length === 4) {

        console.log(req.url);
        console.log(player);

        if (!player) {

          res.statusCode = 302;
          res.setHeader("Location", "/");

          return res.end();
        }

        const [direction] = urlPaths[3];
        const roomID = parseInt(urlPaths[2]);
        const playerRoom = player.currentRoom;
        const currentDirection = player.currentRoom.name[0].toLowerCase();

        if (roomID !== playerRoom.id || (roomID !== playerRoom.id && direction !== currentDirection)) {

          res.statusCode = 302;
          res.setHeader("Location", `/rooms/${player.currentRoom.id}`);

          return res.end();
        }

        let nextRoom;

        try {
          nextRoom = player.move(direction);
        } catch (error) {

          res.statusCode = 302;
          res.setHeader("Location", `/rooms/${playerRoom.id}`);

          return res.end();
        }

        const roomName = nextRoom.name;
        const inventory = player.inventoryToString();
        const items = nextRoom.itemsToString();
        const exit = nextRoom.exitsToString();

        const indexPage = fs.readFileSync("views/room.html", "utf-8");

        let responseBody = indexPage.replace(/#{roomName}/g, roomName);
        responseBody = responseBody.replace(/#{inventory}/g, inventory);
        responseBody = responseBody.replace(/#{roomItems}/g, items);
        responseBody = responseBody.replace(/#{exits}/g, exit);

        res.statusCode = 302;
        res.setHeader("Location", `/rooms/${nextRoom.id}`);

        return res.end(responseBody);
      }
    }

    // Phase 5: POST /items/:itemId/:action
    if (req.method === "POST" && req.url.startsWith("/items/")) {

      const urlPaths = req.url.split("/");

      if (urlPaths.length === 4) {

        if (!player) {

          res.statusCode = 302;
          res.setHeader("Location", "/");

          return res.end();
        }

        const itemID = parseInt(urlPaths[2]);
        const action = urlPaths[3];

        switch (action) {
          case "take":

            try {
              player.takeItem(itemID);
            } catch (error) {

              const page = fs.readFileSync("views/error.html", "utf-8");

              let responseBody = page.replace(/#{errorMessage}/g, error.message);
              responseBody = responseBody.replace(/#{roomId}/g, id);

              res.statusCode = 404;
              res.setHeader("Content-Type", "text/html");

              return res.end(responseBody);
            }

            break;
          case "drop":
            player.dropItem(itemID);
            break;
          case "eat":

            try {
              player.eatItem(itemID);
            } catch (error) {

              const page = fs.readFileSync("views/error.html", "utf-8");

              let responseBody = page.replace(/#{errorMessage}/g, error.message);
              responseBody = responseBody.replace(/#{roomId}/g, id);

              res.statusCode = 200;
              res.setHeader("Content-Type", "text/html");

              return res.end(responseBody);
            }

            break;

          default:
            const page = fs.readFileSync("views/error.html", "utf-8");

            let responseBody = page.replace(/#{errorMessage}/g, "Not a valid action");
            responseBody = responseBody.replace(/#{roomId}/g, id);

            res.statusCode = 404;
            res.setHeader("Content-Type", "text/html");

            return res.end(responseBody);
        }

        const { id } = player.currentRoom;

        res.statusCode = 302;
        res.setHeader("Location", `/rooms/${id}`);
        return res.end();
      }
    }

    if (!player) {
      res.statusCode = 302;
      res.setHeader("Location", "/");

      return res.end();
    }

    // // Phase 6: Redirect if no matching route handlers
    const { id } = player.currentRoom;
    const page = fs.readFileSync("views/error.html", "utf-8");

    let responseBody = page.replace(/#{errorMessage}/g, "Page not found");
    responseBody = responseBody.replace(/#{roomId}/g, id);

    res.statusCode = 404;
    res.setHeader("Content-Type", "text/html");

    return res.end(responseBody);

  })
});

const port = 5000;

server.listen(port, () => console.log('Server is listening on port', port));

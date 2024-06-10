const http = require('http');
const fs = require("fs");

// See https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
const server = http.createServer((req, res) => {

  const index = fs.readFileSync("./index.html", 'utf8');
  const { url } = req;

  // Parsing url when having static files
  if (url !== "/") {
    const paths = url.split('/');
    const [, fileType] = paths[paths.length - 1].split('.');
    const fileName = paths[paths.length - 1];

    switch (fileType) {
      case 'jpg':
        const imgPath = `./assets/images/${fileName}`;
        const img = fs.readFileSync(imgPath);
        res.statusCode = 200;
        res.setHeader('Content-Type', 'image/jpeg');
        return res.end(img);
      case 'css':
        const stylePath = `./assets/css/${fileName}`;
        const style = fs.readFileSync(stylePath, 'utf8');
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/css');
        return res.end(style);
    }
  }

  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/html');
  return res.end(index);
});

const port = 5000;

server.listen(port, () => console.log('Server is listening on port', port));
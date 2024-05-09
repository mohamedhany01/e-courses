const app = require('http');
const PORT = 5666;

app.createServer((req, res) => {
    const responseBody = `
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Hello World!</title>
        </head>
        <body>
            <h1>Hello there!</h1>
        </body>
        </html>
`;

    res.setHeader("Content-Type", "text/html");

    res.end(responseBody);

}).listen(PORT);
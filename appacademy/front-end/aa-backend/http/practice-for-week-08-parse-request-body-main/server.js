const { sendFormPage } = require("./routes");
const { parseBody } = require("./parse-body");
let server;
const PORT = 5000;
const http = require("http");

/******************************************************************************/
/******************* DO NOT CHANGE THE CODE ABOVE THIS LINE *******************/



server = http.createServer((req, res) => {
    let reqData = "";

    req.on("data", (data) => {
        reqData += data;
    });

    req.on("end", () => {
        req.body = parseBody(reqData);
        sendFormPage(req, res);
    });
    
}).listen(PORT, () => console.log('Server is listening on port', PORT));

/******************************************************************************/
/******************* DO NOT CHANGE THE CODE BELOW THIS LINE *******************/

module.exports = { server };
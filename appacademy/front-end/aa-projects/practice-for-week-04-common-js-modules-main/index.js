
const { giveMessageToMrsPotato } = require("./send-messages/give-message-to-mrs-potato");
const { sayHelloTo } = require("./send-messages/say-hello-to");
const { msg1, msg2, msg3 } = require("./messages/index");

/****************************************************************************/
/******************* DO NOT EDIT CODE BELOW THIS LINE ***********************/

sayHelloTo("Mr. Potato");
giveMessageToMrsPotato(msg1);
giveMessageToMrsPotato(msg2);
giveMessageToMrsPotato(msg3);

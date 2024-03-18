const { Food } = require("./food");

class Player {

    constructor(name, startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.items = [];
    }

    move(direction) {

        const nextRoom = this.currentRoom.getRoomInDirection(direction);

        // If the next room is valid, set the player to be in that room
        if (nextRoom) {
            this.currentRoom = nextRoom;

            nextRoom.printRoom(this);
        } else {
            console.log("You cannot move in that direction");
        }
    }

    printInventory() {
        if (this.items.length === 0) {
            console.log(`${this.name} is not carrying anything.`);
        } else {
            console.log(`${this.name} is carrying:`);
            for (let i = 0; i < this.items.length; i++) {
                console.log(`  ${this.items[i].name}`);
            }
        }
    }

    takeItem(itemName) {

        // Copy the item from the room to the player's inventory
        this.items = [...this.currentRoom.items];

        // Find and remove the item from the room array
        this._removeItem(this.currentRoom.items, itemName);
    }

    dropItem(itemName) {

        // Copy the item from the player's inventory to the room
        this.currentRoom.items = [...this.items];

        // Find and remove the item from the player array
        this._removeItem(this.items, itemName);
    }

    eatItem(itemName) {
        const itemIndex = this.items.findIndex((item) => item.name === itemName);
        const item = this.items[itemIndex];

        if (!item.isFood) { return; }

        // Find and remove the item from the player array
        this._removeItem(this.items, itemName);
    }

    getItemByName(name) {
        const [item] = this.items.filter(item => item.name === name);
        return item;
    }

    _removeItem(items, itemName) {
        // Find and remove the item from the array
        const itemIndex = items.findIndex((item) => item.name === itemName);
        items.splice(itemIndex, 1);
    }
}

module.exports = {
    Player,
};

const { Character } = require('./character');


class Enemy extends Character {
  constructor(name, description, currentRoom) {
    super(name, description, currentRoom);
    this.cooldown = 3000;
    this.attackTarget = null;
    this.currentRoom.enemies.push(this);
  }

  setPlayer(player) {
    this.player = player;
  }


  randomMove() {
    const exits = this.currentRoom.getExits();
    if (exits.length > 0) {
      const randomIndex = Math.floor(Math.random() * exits.length);
      const direction = exits[randomIndex];
      const nextRoom = this.currentRoom.getRoomInDirection(direction);
      if (nextRoom) {
        this.currentRoom = nextRoom;
        this.cooldown = 3000; // Reset the cooldown after moving
      }
    }
  }

  takeSandwich() {
    // Fill this in
  }

  // Print the alert only if player is standing in the same room
  alert(message) {
    if (this.player && this.player.currentRoom === this.currentRoom) {
      console.log(message);
    }
  }

  rest() {
    // Wait until cooldown expires, then act
    const resetCooldown = function () {
      this.cooldown = 0;
      this.act();
    };
    setTimeout(resetCooldown, this.cooldown);
  }

  attack() {
    const damageAmount = 10; // Adjust the damage amount as needed
    this.attackTarget.applyDamage(damageAmount);
    this.cooldown = 3000; // Reset the cooldown after an attack
  }

  applyDamage(amount) {
    // Fill this in
  }



  act() {
    if (this.health <= 0) {
      // Dead, do nothing;
    } else if (this.cooldown > 0) {
      this.rest();
    } else {
      this.scratchNose();
      this.rest();
    }

    // Fill this in
  }


  scratchNose() {
    this.cooldown += 1000;

    this.alert(`${this.name} scratches its nose`);

  }


}

module.exports = {
  Enemy,
};

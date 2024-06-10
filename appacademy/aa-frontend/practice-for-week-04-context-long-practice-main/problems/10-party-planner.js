class PartyPlanner {
	constructor() {
		this.guestList = [];
	}

	addToGuestList(name) {
		this.guestList.push(name);
	}

	throwParty() {
		if (this.guestList.length === 0) {
			return "Gotta add people to the guest list";
		}

		return `Welcome to the party ${this.guestList.reduce((prev, curr) => `${prev} and ${curr}`)}`
	}

}


/*****************************************************************************/
/***************** DO NOT MODIFY ANYTHING UNDER THIS LINE ********************/

try {
	module.exports = PartyPlanner;
} catch {
	module.exports = null;
}
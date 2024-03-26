const Calculator = require("./02-calculator");

class FancyCalculator extends Calculator {
	constructor() {
		super();
	}

	setTotal(newTotal) {
		this.total = newTotal;
		return this.total;
	}

	modulo(num) {
		this.total %= num;

		return this.total;
	}

	squared() {
		return this.total * this.total;
	}
}

/*****************************************************************************/
/***************** DO NOT MODIFY ANYTHING UNDER THIS LINE ********************/

try {
	module.exports = FancyCalculator;
} catch {
	module.exports = null;
}
const ValidationError = require('./validation-error');

class MaximumLengthExceededError extends ValidationError {

  constructor(excessLength, message = "Maximum length exceeded", ...args) {
    super(...args);

    this.message = message;

    if (excessLength) {
      this.excessLength = excessLength;
      this.message = `Maximum length exceeded by ${this.excessLength}`;
    }

    this.name = 'MaximumLengthExceededError';
  }

}

/****************************************************************************/
/******************* DO NOT EDIT CODE BELOW THIS LINE ***********************/

try {
  module.exports = MaximumLengthExceededError;
} catch {
  module.exports = null;
}
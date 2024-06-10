class ValidationError extends Error {
  constructor(message = "Invalid input", ...args) {

    // Pass remaining arguments to parent constructor
    super(...args);

    this.message = message;

    // Maintains proper stack trace for where error was thrown (available on V8)
    if (Error.captureStackTrace) {
      Error.captureStackTrace(this, ValidationError);
    }

    // The name property should match the class's name
    this.name = 'ValidationError';

  }
}

/****************************************************************************/
/******************* DO NOT EDIT CODE BELOW THIS LINE ***********************/

try {
  module.exports = ValidationError;
} catch {
  module.exports = null;
}
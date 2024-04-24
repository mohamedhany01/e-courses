class ExampleError extends Error {
    constructor(message, ...args) {

        // Must to passing the arguments
        super(...args);

        // Maintains proper stack trace for where error was thrown (available on V8)
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, ExampleError);
        }

        // If you leave this without a custom name then the default will be "Error"
        this.name = "ExampleError";

        // If you leave this without a custom message then the default will be empty string
        // when the error is thrown
        this.message = message;

        // Extra custom debugging information
        this.date = new Date();

    }
}

// The custom error in action
try {
    if (true) {
        throw new ExampleError("Error is thrown");
    }
} catch (e) {
    console.log(e.name);
    console.log(e.message);
    console.log(e.date);
}
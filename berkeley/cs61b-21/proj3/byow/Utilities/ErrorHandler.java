package byow.Utilities;

public class ErrorHandler {
    public ErrorHandler() {
    }

    public void throwError(String message, int index) {

        throw new RuntimeException("Unknown command " + message + " at " + index);
    }

    public void throwError(String message) {
        throw new RuntimeException(message);
    }
}

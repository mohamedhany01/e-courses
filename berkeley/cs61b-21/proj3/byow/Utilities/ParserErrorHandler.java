package byow.Utilities;

public class ParserErrorHandler {
    public ParserErrorHandler() {
    }

    public void throwError(String command, int index) {

        throw new RuntimeException("Unknown command " + command + " at " + index);
    }

    public void throwError(String command) {
        throw new RuntimeException(command);
    }
}

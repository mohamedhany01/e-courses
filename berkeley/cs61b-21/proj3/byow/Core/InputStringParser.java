package byow.Core;

import byow.Utilities.*;

import java.util.LinkedList;
import java.util.Queue;

public class InputStringParser {
    private final String token;

    public InputStringParser(String token) {
        this.token = token;
    }

    public GameConfigurations getConfigurations() {
        final String fullCommand = this.token.toLowerCase();
        final String zeroIndexCommand = String.valueOf(fullCommand.charAt(0));
        GameConfigurations configurations = new GameConfigurations();

        if (zeroIndexCommand.equals("n")) {
            System.out.println("New world");

            int startPosition = fullCommand.indexOf("s") + 1;

            configurations.setSeed(getSeed(fullCommand));
            configurations.setInstructions(this.extractInstructions(startPosition, fullCommand));

            return configurations;

        } else if (zeroIndexCommand.equals("l")) {
            System.out.println("Load world");
        } else {
            ErrorHandler handler = new ErrorHandler();
            handler.throwError(String.valueOf(fullCommand.charAt(0)), 0);
        }

        return null;
    }


    private Queue<Instruction> extractInstructions(int startPosition, String fullCommand) {
        CommandTable table = new CommandTable();
        Queue<Instruction> instructions = new LinkedList<>();
        int len = fullCommand.length();
        int nextIndex = startPosition;

        while (true) {

            if (nextIndex == len || nextIndex > len) {
                break;
            }

            Character command = fullCommand.charAt(nextIndex);

            // Special case "Quit"
            if (nextIndex < len && table.getCommand(command) == Command.QUIT) {

                boolean isValidQuit = fullCommand.charAt(nextIndex + 1) == 'q';

                if (!isValidQuit) {
                    ErrorHandler errorHandler = new ErrorHandler();
                    errorHandler.throwError(String.valueOf(fullCommand.charAt(nextIndex + 1)), nextIndex);
                }

                Instruction instruction1 = new Instruction(table.getCommand(command));
                instructions.add(instruction1);
                instruction1.incrementCount();
                break;
            }

            if (table.getCommand(command) == Command.UNKNOWN) {
                ErrorHandler errorHandler = new ErrorHandler();

                errorHandler.throwError(String.valueOf(command), nextIndex);

            }

            Command currentCommand = table.getCommand(command);
            Instruction instruction = new Instruction(currentCommand);
            instructions.add(instruction);

            while (nextIndex < len && table.getCommand(fullCommand.charAt(nextIndex)) == currentCommand) {
                instruction.incrementCount();
                ++nextIndex;
            }
        }

        return instructions;
    }

    @Override
    public String toString() {
        return "InputStringParser{" +
                "token='" + token + '\'' +
                '}';
    }

    private int getSeed(String token) {
        int nextIndex = 1;
        if (!Character.isDigit(token.charAt(nextIndex))) {
            return -1;
        }

        final StringBuilder seed = new StringBuilder();
        final String tokenSmall = token.toLowerCase();

        // String from index 1 to get the seed
        Character c = tokenSmall.charAt(nextIndex);
        while (Character.isDigit(c)) {
            seed.append(c);
            c = tokenSmall.charAt(++nextIndex);
        }

        Character command = tokenSmall.charAt(nextIndex);

        if (command != 's') {
            ErrorHandler handler = new ErrorHandler();
            handler.throwError(String.valueOf(command), nextIndex);
        }

        return Integer.parseInt(seed.toString());
    }

}

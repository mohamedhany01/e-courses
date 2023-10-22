package byow.Utilities;

public class CommandTable {
    public Command getCommand(Character c) {
        switch (c){
            case 'w':
                return Command.UP;
            case 'd':
                return Command.RIGHT;
            case 'a':
                return Command.LEFT;
            case 's':
                return Command.DOWN;
            case ':':
                return Command.QUIT;
            default:
                return Command.UNKNOWN;
        }
    }
}

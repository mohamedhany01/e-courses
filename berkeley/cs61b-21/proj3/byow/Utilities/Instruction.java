package byow.Utilities;

public class Instruction {
    private Command command;
    private int count;

    public Instruction(Command command) {
        this.command = command;
        this.count = 0;
    }

    public void incrementCount() {
        this.count += 1;
    }

    public Command getCommand() {
        return command;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "command=" + command +
                ", count=" + count +
                '}';
    }
}

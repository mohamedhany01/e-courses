package byow.Utilities;

import java.util.Queue;

public class GameConfigurations {
    private Queue<Instruction> instructions;
    private int seed;

    public Queue<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(Queue<Instruction> instructions) {
        this.instructions = instructions;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "GameConfigurations{" +
                "instructions=" + instructions +
                ", seed=" + seed +
                '}';
    }
}

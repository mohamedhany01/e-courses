package byow.lab13;

public class Settings {

    private long seed = -1L;
    private static Settings instance;
    /**
     * Whether or not the game is over.
     */
    private boolean isGameOver = false;
    private String word = "";
    /**
     * The current round the user is on.
     */
    private int round = 1;
    private String state = "";

    private Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
            return instance;
        }
        return instance;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void incrementRound() {
        this.round++;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "isGameOver=" + isGameOver +
                ", word='" + word + '\'' +
                ", round=" + round +
                ", state=" + state +
                '}';
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }
}

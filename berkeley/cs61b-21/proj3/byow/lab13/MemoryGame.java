package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private final int width;
    /** The height of the window of this game. */
    private final int height;

    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {
            "You can do this!",
            "I believe in you!",
            "You got this!",
            "You're a star!",
            "Go Bears!",
            "Too easy for you!",
            "Wow, so impressive!",
            "Keep up the great work!",
            "Incredible performance!",
            "Unstoppable!",
            "Impressive skills!",
            "Amazing job!",
            "You're on fire!",
            "Top-notch!",
            "Fantastic effort!",
            "You're the best!",
            "Absolutely outstanding!",
            "Bravo!",
            "Magnificent!",
            "Brilliant!",
            "Spectacular!",
            "Outstanding work!",
            "Exceptional!",
            "Way to go!",
            "You're a champ!",
            "Superb!",
            "Well done!",
            "Astonishing!",
            "Kudos to you!",
            "Excellent!",
            "Remarkable!",
            "Stellar!",
            "First-rate!",
            "Phenomenal!",
            "Terrific job!",
            "Exceptional talent!",
            "Dazzling!",
            "Inspirational!",
            "You're a genius!",
            "Impressive performance!",
    };
    private static Settings settings;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(55, 55, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        String word = this.generateRandomString((int) seed);

        settings = Settings.getInstance();
        settings.setState(this.getState(State.WATCH));
        settings.setWord(word);
        settings.setRound(1);
        settings.setGameOver(false);
        settings.setSeed(seed);
    }

    public String generateRandomString(int seed) {
        //TODO: Generate random string of letters of length n
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        int charsLen = CHARACTERS.length;

        for (int i = 0; i < seed; i++) {
            Character c = CHARACTERS[random.nextInt(charsLen)];
            randomStr.append(c);
        }

        return randomStr.toString();
    }

    public void drawFrame(String s) {
        // Take the string and display it in the center of the screen
        int x = this.width / 2;
        int y = this.height / 2;

        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        for (Character c: letters.toCharArray()) {
            StdDraw.clear(Color.BLACK);
            this.showHUD(settings.getRound(), settings.getState(), getEncourage());
            this.drawFrame(Character.toString(c));
            StdDraw.pause(WaitTime.ONE_AND_A_HALF_SECONDS.value);
            this.drawFrame("");
            StdDraw.pause(WaitTime.HALF_SECOND.value);
        }
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        StringBuilder word = new StringBuilder();

        while (word.toString().length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                Character c = StdDraw.nextKeyTyped();
                word.append(c);

                // Update the game UI while typing
                StdDraw.clear(Color.BLACK);
                this.showHUD(settings.getRound(), settings.getState(), getEncourage());
                drawFrame(word.toString());
            }
        }
        return word.toString();
    }

    public void startGame() {
        // Set any relevant variables before the game starts
        String currentStatus = settings.getState();
        String currentWord = settings.getWord();
        int currentRound = settings.getRound();
        int inputLength = (int) settings.getSeed();

        // Establish Engine loop
        do {
            // Show current round
            this.clearCanvas();
            this.showHUD(currentRound, currentStatus, getEncourage());
            this.drawFrame("Round: " + currentRound);
            StdDraw.pause(WaitTime.ONE_SECOND.value);

            // Start to display chars for the user one by one and HUD activated as well
            this.flashSequence(currentWord);

            // Clear chars if the user typed anything while display flashed chars
            this.clearCharactersBuffer();

            // Change status to Type!
            currentStatus = this.getState(State.TYPE);
            settings.setState(currentStatus);
            this.clearCanvas();
            this.showHUD(currentRound, currentStatus, getEncourage());

            // Get the user input
            String userInput = this.solicitNCharsInput(inputLength);

            // Add some delay
            StdDraw.pause(WaitTime.HALF_SECOND.value);

            // Move to the next turn
            if (this.isRightAnswer(userInput)) {
                currentWord = this.generateRandomString((int) settings.getSeed());
                currentStatus = this.getState(State.WATCH);

                settings.setWord(currentWord);
                settings.setState(currentStatus);
                settings.incrementRound();

                currentRound = settings.getRound();

            } else {
                StdDraw.clear(Color.BLACK);
                this.drawFrame("Game over! You made it to round: " + settings.getRound());
                settings.setGameOver(true);
            }
        } while (!settings.isGameOver());
    }

    private void showHUD(int round, String state, String encourage) {

        // The line under the UI statistics
        StdDraw.line(0, this.height - 2, this.width, this.width - 2);

        // The round number text
        this.drawFrame(new Position(5, this.height - 1), "Round: " + round);

        // The game states (Watch/type)
        this.drawFrame(new Position(this.height / 2, this.height - 1), state);

        // The encourage string
        this.drawFrame(new Position(this.height - 9, this.height - 1), encourage);

        StdDraw.show();
    }

    public void drawFrame(Position p, String s) {
        StdDraw.text(p.getX(), p.getY(), s);
    }

    private String getEncourage() {
        Random r = new Random();
        return ENCOURAGEMENT[r.nextInt(ENCOURAGEMENT.length - 1)];
    }

    private String getState(State s) {
        return switch (s) {
            case TYPE -> "Type!";
            case WATCH -> "Watch!";
        };
    }

    private boolean isRightAnswer(String answer) {
        return settings.getWord().equals(answer);
    }

    private void clearCanvas() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
    }

    private void clearCharactersBuffer() {
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
    }

}

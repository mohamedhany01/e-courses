package byow.Core;

import byow.Core.gui.Window;
import byow.Core.gui.WindowBuilder;
import byow.Core.world.World;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab13.Position;

public class Engine {
    public Engine() {
    }

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        World world = new World(WIDTH, HEIGHT, Tileset.TREE);
        ter.renderFrame(world.getWorld());
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        int optionMargin = 40;
        Window mainMenu = new Window(WIDTH, HEIGHT);
        WindowBuilder builder = new WindowBuilder(mainMenu);
        builder
        .setTitle("The Game", 50, 50)
        .setOption("New Game (N)", optionMargin -=2, 20)
        .setOption("Load Game (L)", optionMargin -=2, 20)
        .setOption("Quit (Q)", optionMargin -=2, 20)
        .render();

       String userInput =  mainMenu.getUserInput(1);

        if (userInput.equals("n")) {
            builder.setTitle("Enter World Seed:", 50, 50).render();
            String userSeed = mainMenu.printInputOn(
                new Position(mainMenu.getWidth()/2, 45),
                "Enter World Seed:",
                new Position(mainMenu.getWidth()/2, 50)
            );
            int seed = Integer.parseInt(userSeed);

            builder.setTitle("Starting Game", 50, 50).render();
        } else if (userInput.equals("q")) {
            System.exit(0);
        }


//        ter.initialize(WIDTH, HEIGHT);
//        World world = new World(WIDTH, HEIGHT);
//
//        InputStringParser parser = new InputStringParser(input);
//        GameConfigurations configurations = parser.getConfigurations();
//        System.out.println(configurations.getSeed());
//        System.out.println(configurations.getInstructions());
//        ter.renderFrame(world.getWorld());

        return null;
    }
}

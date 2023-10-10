package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private TERenderer renderer;

    private TETile[][] world;

    public HexWorld() {
        this.initEmptyWorld();
        this.drawWorldOnScreen();
    }

    public HexWorld(int startX, int startY, int columns, int hexSize) {
        this.initEmptyWorld();
        this.drawHexWorld(startX, startY, columns, hexSize);
        this.drawWorldOnScreen();
    }

    private void initEmptyWorld() {
        this.renderer = new TERenderer();
        this.world = new TETile[WIDTH][HEIGHT];

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        this.renderer.initialize(WIDTH, HEIGHT);

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                this.world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void drawWorldOnScreen() {
        // draws the world to the screen
        this.renderer.renderFrame(this.world);
    }

    private void drawHexWrapper(int startX, int startY, int size, TETile type) {
        int blanksCount = size > 2 ? size - 1 : size;

        drawHexUpper(this.world, type, startX, startY, blanksCount, size, size);

        drawHexDown(this.world, type, startX, startY - (size * 2 - 1), blanksCount, size, size);

    }

    private void drawHexUpper(TETile[][] world, TETile type, int startX, int startY, int blanks, int patterns, int stopAfter) {
        int end = startX + blanks; // Where to stop printing blank spaces, and start printing the pattern
        int endCurrentRow = end + patterns; // Where the current row ends and the beginning of the next row
        int padding = 2; // Number of pattern added each time to the ends

        if (stopAfter > 0) {

            // Spaces | Debugging
//            for (int x = startX; x < end; x++) {
//                world[x][startY] = Tileset.FLOWER;
//            }

            // Patterns
            for (int x = startX + blanks; x < endCurrentRow; x++) {
                world[x][startY] = type;
            }

            drawHexUpper(world, type, startX, --startY, --blanks, patterns + padding, --stopAfter);
        }
    }

    private void drawHexDown(TETile[][] world, TETile type, int startX, int startY, int blanks, int patterns, int stopAfter) {
        int end = startX + blanks; // Where to stop printing blank spaces, and start printing the pattern
        int endCurrentRow = end + patterns; // Where the current row ends and the beginning of the next row
        int padding = 2; // Number of pattern added each time to the ends

        if (stopAfter > 0) {

            // Spaces | Debugging
//            for (int x = startX; x < end; x++) {
//                world[x][startY] = Tileset.FLOOR;
//            }

            // Patterns
            for (int x = startX + blanks; x < endCurrentRow; x++) {
                world[x][startY] = type;
            }

            drawHexDown(world, type, startX, ++startY, --blanks, patterns + padding, --stopAfter);
        }
    }

    private void drawColumnHex(int x, int y, int columns, int hexSize) {
        for (int i = 0; i < columns; i++) {
            this.drawHexWrapper(x, y, hexSize, getRandom());
            y = y - (hexSize * 2); // Next anchor point on Y-axis
        }
    }

    private void drawHexWorld(int x, int y, int columns, int hexSize) {
        int columnsToDraw = columns;

        for (int i = 0; i < columns; i++) {
            drawColumnHex(x, y, columnsToDraw, hexSize);
            ++columnsToDraw;
            x = x + (hexSize * 2 - 1);
            y = y + hexSize;
        }

        // Reset the point to continue the other half
        columnsToDraw = columnsToDraw - 2;
        x = x + hexSize - hexSize;
        y = y - hexSize * 2;


        for (int i = 0; i < columns - 1; i++) {
            drawColumnHex(x, y, columnsToDraw, hexSize);
            --columnsToDraw;
            x = x + (hexSize * 2 - 1);
            y = y - hexSize;
        }
    }

    private TETile getRandom() {
        int random = new Random().nextInt(11);

        switch (random) {
            case 0:
                return Tileset.AVATAR;
            case 1:
                return Tileset.WALL;
            case 2:
                return Tileset.FLOOR;
            case 3:
                return Tileset.GRASS;
            case 4:
                return Tileset.WATER;
            case 5:
                return Tileset.FLOWER;
            case 6:
                return Tileset.LOCKED_DOOR;
            case 7:
                return Tileset.UNLOCKED_DOOR;
            case 8:
                return Tileset.SAND;
            case 9:
                return Tileset.MOUNTAIN;
            case 10:
                return Tileset.TREE;
            default:
                return Tileset.NOTHING;
        }

    }
}

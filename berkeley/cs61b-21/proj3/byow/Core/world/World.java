package byow.Core.world;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class World {
    private TETile[][] world;

    public World(int width, int height) {
        this.world = new TETile[width][height];
        this.world = this.initWorld(this.world, width, height);
    }

    public World(int width, int height, TETile type) {
        this.world = new TETile[width][height];
        this.world = this.initWorld(this.world, width, height, type);
    }

    private TETile[][] initWorld(TETile[][] emptyWorld, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return emptyWorld;
    }

    private TETile[][] initWorld(TETile[][] emptyWorld, int width, int height, TETile type) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = type;
            }
        }
        return emptyWorld;
    }

    public TETile[][] getWorld() {
        return this.world;
    }

}

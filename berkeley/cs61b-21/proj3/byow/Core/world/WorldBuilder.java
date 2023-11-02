package byow.Core.world;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class WorldBuilder {
    private World world;

    public WorldBuilder(World world) {
        this.world = world;
    }

    public WorldBuilder setWorldTitle(String title) {
//        TETile[][] tempWorld = this.world.getWorld();
//        TERenderer teRenderer = new TERenderer();
//        teRenderer.
        return this;
    }

    public World getWorld() {
        return this.world;
    }
}

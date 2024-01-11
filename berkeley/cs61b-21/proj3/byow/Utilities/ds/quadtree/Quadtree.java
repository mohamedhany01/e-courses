package byow.Utilities.ds.quadtree;

import byow.Core.world.Point;

import java.util.LinkedList;

public class Quadtree {
    public Section section;
    public Dungeon parent;
    public Point center;
    public boolean isDungeon;
    public boolean isLeaf;
    public LinkedList<Quadtree> children;

    public Quadtree(Dungeon dungeon) {
        this.parent = dungeon;
        this.children = new LinkedList<>();
    }

    public Quadtree(Dungeon rectangle, Section section) {
        this.parent = rectangle;
        this.children = new LinkedList<>();
        this.section = section;
        this.isLeaf = false;
        this.isDungeon = false;
    }

    @Override
    public String toString() {
        return "Quadtree{" +
                "section=" + section +
                ", overseer=" + parent +
                ", center=" + center +
                ", children=" + children +
                '}';
    }

    private void split(Quadtree node, int remaining) {

        if (remaining <= 0) return;

        int x = node.parent.x1;
        int y = node.parent.y1;

        int width = node.parent.x2 - node.parent.x1;
        int height = node.parent.y2 - node.parent.y1;

        // Calculate midpoints for the current quadrant
        int midX = x + width / 2;
        int midY = y + height / 2;

        node.center = new Point(midX, midY);

        // Northeast data
        Dungeon northEastRectangle = new Dungeon(midX, midY, x + width, y + height);
        Quadtree northEast = new Quadtree(northEastRectangle, Section.TOP_RIGHT);
        node.children.add(northEast);
        split(northEast, remaining - 1);

        // Northwest data
        Dungeon northWestRectangle = new Dungeon(x, midY, midX, y + height);
        Quadtree northWest = new Quadtree(northWestRectangle, Section.TOP_LEFT);
        node.children.add(northWest);
        split(northWest, remaining - 1);

        // Southwest data
        Dungeon southWestRectangle = new Dungeon(x, y, midX, midY);
        Quadtree southWest = new Quadtree(southWestRectangle, Section.BOTTOM_LEFT);
        node.children.add(southWest);
        split(southWest, remaining - 1);

        // Southeast data
        Dungeon southEastRectangle = new Dungeon(midX, y, x + width, midY);
        Quadtree southEast = new Quadtree(southEastRectangle, Section.BOTTOM_RIGHT);
        node.children.add(southEast);
        split(southEast, remaining - 1);
    }

    public void split(int depth) {
        split(this, depth);
    }
}

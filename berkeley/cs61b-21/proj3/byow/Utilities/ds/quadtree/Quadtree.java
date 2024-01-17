package byow.Utilities.ds.quadtree;

import byow.Core.world.Point;

import java.util.LinkedList;

public class Quadtree {
    public Section section;
    public Dungeon parent;
    public Point center;
    public LinkedList<Quadtree> children;
    public boolean isDungeon;
    public boolean isLeaf;


    public Quadtree(Dungeon dungeon) {
        this.parent = dungeon;
        this.children = new LinkedList<>();
        this.isLeaf = false;
        this.isDungeon = false;
    }

    public Quadtree(Dungeon rectangle, Section section) {
        this.parent = rectangle;
        this.children = new LinkedList<>();
        this.section = section;
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
        int x = node.parent.x1;
        int y = node.parent.y1;

        int width = node.parent.x2 - node.parent.x1;
        int height = node.parent.y2 - node.parent.y1;

        if (remaining <= 0) {
            calculateCenter(node, x, y, width, height);
            return;
        }

        // Calculate midpoints for the current quadrant
        calculateCenter(node, x, y, width, height);

        // Northeast data
        Dungeon northEastRectangle = new Dungeon(node.center.getX(), node.center.getY(), x + width, y + height);
        Quadtree northEast = new Quadtree(northEastRectangle, Section.TOP_RIGHT);
        node.children.add(northEast);
        split(northEast, remaining - 1);

        // Northwest data
        Dungeon northWestRectangle = new Dungeon(x, node.center.getY(), node.center.getX(), y + height);
        Quadtree northWest = new Quadtree(northWestRectangle, Section.TOP_LEFT);
        node.children.add(northWest);
        split(northWest, remaining - 1);

        // Southwest data
        Dungeon southWestRectangle = new Dungeon(x, y, node.center.getX(), node.center.getY());
        Quadtree southWest = new Quadtree(southWestRectangle, Section.BOTTOM_LEFT);
        node.children.add(southWest);
        split(southWest, remaining - 1);

        // Southeast data
        Dungeon southEastRectangle = new Dungeon(node.center.getX(), y, x + width, node.center.getY());
        Quadtree southEast = new Quadtree(southEastRectangle, Section.BOTTOM_RIGHT);
        node.children.add(southEast);
        split(southEast, remaining - 1);
    }

    private void calculateCenter(Quadtree node, int x, int y, int width, int height) {
        int midX = x + width / 2;
        int midY = y + height / 2;

        node.center = new Point(midX, midY);
    }

    public void split(int depth) {
        split(this, depth);
    }
}

package byow.Core.world;

import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.Utilities.ds.quadtree.Dungeon;
import byow.Utilities.ds.quadtree.Quadtree;
import byow.Utilities.ds.triangulation.DelaunayTriangulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldMap {
    private final TETile[][] world;
    private final int width;
    private final int height;

    public WorldMap(int width, int height) {
        this.world = new TETile[width][height];
        this.width = width;
        this.height = height;

        this.initWorld(this.world, width, height);

        // Create the tree and split it 3 levels
        Quadtree quadtree = new Quadtree(new Dungeon(0, 0, width, height));

        // The split-level is fine for 60x60 grid
        quadtree.split(2);

        // TODO: Temp logic for static dungeons | remove it
        int[] numbers = {
                3, 1, 2, 1, 2, 1, 1, 2, 3, 3,
                3, 2, 2, 2, 3, 2, 2, 2, 1, 1, 1
        };
        int counter = 0;
        // END temp logic

        LinkedList<Quadtree> dungeons = new LinkedList<>();
        drawInnerDungeons(quadtree, dungeons, counter, numbers);

        // Start to build a mesh using the delaunay triangulation
        DelaunayTriangulation triangulationBuilder = new DelaunayTriangulation();

        // Get the edges of the mesh
        List<Edge> edges = triangulationBuilder.getEdges(dungeons);

        System.out.println(edges);

        // Debug the sections
        //this.drawAllSections(quadtree);
    }

    // Get the split sections in the world and draw the dungeons inside
    private void drawInnerDungeons(Quadtree qt, LinkedList<Quadtree> dungeons, int counter, int[] numbers) {
        // Draw randomly using this random number
        int drawDungeon = RandomUtils.uniform(new Random(), 4);

        // Set tree node a leaf
        if (qt.children.isEmpty()) {
            qt.isLeaf = true;
        }

        // Using the tree leafs to draw the dungeons
        // TODO: revert this if (qt.children.isEmpty() && drawDungeon == 1)
        if (qt.children.isEmpty() && numbers[counter] == 1) {
            counter++;
            qt.isDungeon = true;

            // Collect all valid dungeons to process them later
            dungeons.add(qt);

            // qt.parent.x2 - 2 and qt.parent.y2 - 2 and qt.center.getX() - 1 ...etc
            // Are for padding
            for (int x = qt.parent.x1; x < qt.parent.x2 - 2; x++) {
                for (int y = qt.parent.y1; y < qt.parent.y2 - 2; y++) {
                    if (x == qt.parent.x1 || x == qt.parent.x2 - 3 || y == qt.parent.y1 || y == qt.parent.y2 - 3) {
                        world[x][y] = Tileset.WALL;

                        // Debug the center point of the dungeons
                        world[qt.center.getX() - 1][qt.center.getY() - 1] = Tileset.WALL;
                    }
                }
            }

        }

        // Skip parents
        for (Quadtree quadtree : qt.children) {
            drawInnerDungeons(quadtree, dungeons, ++counter, numbers);
        }
    }

    private void drawAllSections(Quadtree qt) {
        if (qt.parent == null) return;
        drawSection(qt);
    }

    private void drawSection(Quadtree quadtree) {
        if (quadtree.children.isEmpty()) return;

        int x = quadtree.center.getX();
        int y = quadtree.center.getY();


        TETile fixed = getRandom();

        // Left line
        for (int i = quadtree.parent.x1 + 1; i < x; i++) {
            world[i][y] = fixed;
        }

        // Right line
        for (int i = x; i < quadtree.parent.x2; i++) {
            world[i][y] = fixed;
        }

        // Bottom line
        for (int i = quadtree.parent.y1 + 1; i < y; i++) {
            world[x][i] = fixed;
        }

        // Top line
        for (int i = y + 1; i < quadtree.parent.y2; i++) {
            world[x][i] = fixed;
        }

        // Draw children recursively
        for (Quadtree qt : quadtree.children) {
            this.drawSection(qt);
        }

    }

    private TETile getRandom() {
        int random = new Random().nextInt(11);

        return switch (random) {
            case 0 -> Tileset.AVATAR;
            case 1 -> Tileset.WALL;
            case 2 -> Tileset.FLOOR;
            case 3 -> Tileset.GRASS;
            case 4 -> Tileset.WATER;
            case 5 -> Tileset.FLOWER;
            case 6 -> Tileset.LOCKED_DOOR;
            case 7 -> Tileset.UNLOCKED_DOOR;
            case 8 -> Tileset.SAND;
            case 9 -> Tileset.MOUNTAIN;
            case 10 -> Tileset.TREE;
            default -> Tileset.NOTHING;
        };

    }

    private void initWorld(TETile[][] emptyWorld, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                emptyWorld[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

//    private

    /*
     *   Build maze: https://www.youtube.com/watch?v=Y37-gB83HKE
     * */
//    private void buildMaze2(TETile[][] world, int width, int height) {
//        LinkedList<Point> points = new LinkedList<>();
//
//        for (int i = 0; i < 30; i++) {
//            Random x = new Random();
//            Random y = new Random();
//            Point newPoint = new Point(x.nextInt(width), y.nextInt(height));
//
//            boolean hasThisCell = points.contains(newPoint);
//            while (hasThisCell) {
//                newPoint = new Point(x.nextInt(width), y.nextInt(height));
//                hasThisCell = points.contains(newPoint);
//            }
//            points.add(newPoint);
//        }
//
//        for (Point c : points) {
//            LinkedList<Point> allN = getCellNeighbors(c);
//            this.world[c.getX()][c.getY()] = Tileset.WALL;
//            for (Point nb : allN) {
//                this.world[nb.getX()][nb.getY()] = Tileset.WALL;
//            }
//
//        }
//    }
//
//    private void buildMaze(TETile[][] world, int width, int height) {
//        Point next = new Point(0, 0);
//        Stack<Point> stack = new Stack<>();
//        HashMap<Point, Boolean> visited = new HashMap<>();
//        stack.add(next);
//        visited.put(next, true);
//
//        world[next.getX()][next.getY()] = Tileset.SAND;
//
//        while (visited.size() <= width * height - 1) {
//
//
//            LinkedList<Point> nextNeighbors = getCellNeighbors(next, visited);
//            next = getRandomNeighbors(nextNeighbors);
//
//            if (this.isVisited(visited, next)) {
//                continue;
//            }
//
//            visited.put(next, true);
//            stack.add(next);
//            world[next.getX()][next.getY()] = Tileset.SAND;
//
//            boolean isAllNeighborsOfNextVisited = isAllNeighborsVisited(next, visited);
//
//            while (isAllNeighborsOfNextVisited  && !stack.isEmpty()) {
//                next = stack.pop();
//                isAllNeighborsOfNextVisited = isAllNeighborsVisited(next, visited);
//            }
//
//        }
//    }
//
//    private boolean isVisited(HashMap<Point, Boolean> visited, Point point) {
//        return visited.containsKey(point);
//    }
//
//    private Point getRandomNeighbors(LinkedList<Point> neighbors) {
//        Random r = new Random();
//        int num = r.nextInt(neighbors.size());
//
//        return neighbors.get(num);
//    }
//
//    private LinkedList<Point> getCellNeighbors(Point c, HashMap<Point, Boolean> visited) {
//        LinkedList<Point> neighbors = new LinkedList<>();
//        int [] x4 = new int[]{-1, 0, 1, 0};
//        int [] y4 = new int[] {0, 1, 0, -1};
//        int len = x4.length;
//
//        for (int i = 0; i < len; i++) {
//            int xNeighbor =  c.getX() + x4[i];
//            int yNeighbor = c.getY() + y4[i];
//
//            if (visited.containsKey(new Point(xNeighbor, yNeighbor)) || (xNeighbor == c.getX() && yNeighbor == c.getY()) || xNeighbor < 0 || yNeighbor < 0 || xNeighbor >= this.width || yNeighbor >= this.height) {
//                continue;
//            }
//
//            neighbors.add(new Point(xNeighbor, yNeighbor));
//
//        }
//
//        return neighbors;
//    }
//
//    private LinkedList<Point> getCellNeighbors(Point c) {
//        LinkedList<Point> neighbors = new LinkedList<>();
//        int [] x4 = new int[]{-1, 0, 1, 0};
//        int [] y4 = new int[] {0, 1, 0, -1};
//        int len = x4.length;
//
//        for (int i = 0; i < len; i++) {
//            int xNeighbor =  c.getX() + x4[i];
//            int yNeighbor = c.getY() + y4[i];
//
//            if ((xNeighbor == c.getX() && yNeighbor == c.getY()) ||  xNeighbor < 0 || yNeighbor < 0 || xNeighbor >= this.width ||  yNeighbor >= this.height) {
//                continue;
//            }
//
//            neighbors.add(new Point(xNeighbor, yNeighbor));
//
//        }
//
//        return neighbors;
//    }
//
//    private boolean isAllNeighborsVisited(Point point, HashMap<Point, Boolean> visited) {
//        LinkedList<Point> allNeighbors = getCellNeighbors(point, visited);
//
//        for (Point c : allNeighbors) {
//            if (!visited.containsKey(c)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

}

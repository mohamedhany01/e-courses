package byow.Core.world;

import byow.Utilities.ds.triangulation.CoordinateEdge;

public class Edge {
    private final Point p1;
    private final Point p2;
    private final int weight;

    public Edge(CoordinateEdge coordinate) {
        this.p1 = coordinate.getStartPoint();
        this.p2 = coordinate.getEndPoint();
        this.weight = coordinate.getWeight();
    }

    public int getWeight() {
        return weight;
    }

    public Point getStartPoint() {
        return p1;
    }

    public Point getEndPoint() {
        return p2;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", weight=" + weight +
                '}' + "\n";
    }
}

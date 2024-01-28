package byow.Core.world;

import byow.Utilities.ds.triangulation.CoordinateEdge;

public class Edge {
    private final Point p1;
    private final Point p2;

    public Edge(CoordinateEdge coordinate) {
        this.p1 = coordinate.getStartPoint();
        this.p2 = coordinate.getEndPoint();
    }

    @Override
    public String toString() {
        return "Edge{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}

package byow.Utilities.ds.triangulation;

import byow.Core.world.Point;
import org.locationtech.jts.geom.Coordinate;

public class CoordinateEdge {
    private final Coordinate startPoint;
    private final Coordinate endPoint;
    private final Weight weight;

    public CoordinateEdge(Coordinate start, Coordinate end) {
        this.startPoint = start;
        this.endPoint = end;
        this.weight = new Weight(start, end);
    }

    public Point getStartPoint() {
        return new Point((int) startPoint.x, (int) startPoint.y);
    }

    public Point getEndPoint() {
        return new Point((int) endPoint.x, (int) endPoint.y);
    }

    public int getWeight() {
        return weight.getWeight();
    }

    @Override
    public String toString() {
        return "CoordinateEdge{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", weight=" + weight +
                '}';
    }
}

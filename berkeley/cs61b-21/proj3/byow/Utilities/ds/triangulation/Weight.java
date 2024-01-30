package byow.Utilities.ds.triangulation;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.operation.distance.DistanceOp;

public class Weight {
    private final DistanceOp distance;

    public Weight(Coordinate start, Coordinate end) {
        this.distance = new DistanceOp(
                new GeometryFactory().createPoint(start),
                new GeometryFactory().createPoint(end)
        );
    }

    public int getWeight() {
        return (int) this.distance.distance();
    }

    @Override
    public String toString() {
        return "Weight{" +
                "distance=" + distance +
                '}';
    }
}

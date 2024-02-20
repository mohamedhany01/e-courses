package byow.Utilities.ds.triangulation;

import byow.Core.world.Point;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class PathsExtractor {

    public PathsExtractor() {
    }

    private List<List<Point>> getPaths(List<List<Point>> edgePaths, Coordinate start, Coordinate end) {
        List<Point> path = new ArrayList<>();
        int x0 = (int) start.getX();
        int y0 = (int) start.getY();
        int x1 = (int) end.getX();
        int y1 = (int) end.getY();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (x0 != x1 || y0 != y1) {
            path.add(new Point(x0, y0));
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
        path.add(new Point(x1, y1));
        edgePaths.add(path);

        return edgePaths;
    }

    public List<List<Point>> getEdgePaths(Coordinate start, Coordinate end) {
        List<List<Point>> edgePaths = new ArrayList<>();
        return getPaths(edgePaths, start, end);
    }

}

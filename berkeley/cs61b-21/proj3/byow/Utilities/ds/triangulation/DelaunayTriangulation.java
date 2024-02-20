package byow.Utilities.ds.triangulation;

import byow.Core.world.Edge;
import byow.Core.world.Point;
import byow.Utilities.ds.graph.EdgeNode;
import byow.Utilities.ds.quadtree.Quadtree;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.ConformingDelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;

import java.util.*;

public class DelaunayTriangulation {
    private Map<EdgeNode, List<Point>> corridorsMap;

    public List<Edge> getEdges(LinkedList<Quadtree> dungeons) {
        PathsExtractor extractor = new PathsExtractor();

        List<CoordinateEdge> edgeList = performDelaunayTriangulation(dungeons, extractor);
        List<Edge> edges = new ArrayList<>();

        for (CoordinateEdge edge : edgeList) {
            edges.add(new Edge(edge));
        }

        return edges;
    }

    private List<CoordinateEdge> performDelaunayTriangulation(LinkedList<Quadtree> dungeons, PathsExtractor extractor) {
        List<Coordinate> points = new ArrayList<>();

        // Extract points from Quadtree dungeons
        for (Quadtree dungeon : dungeons) {
            points.add(new Coordinate(dungeon.center.getX(), dungeon.center.getY()));
        }

        // Perform Delaunay triangulation
        ConformingDelaunayTriangulationBuilder builder = new ConformingDelaunayTriangulationBuilder();

        // Create a Point array using GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();
        Geometry pointsGeometry = geometryFactory.createMultiPointFromCoords(points.toArray(new Coordinate[0]));

        // Set sites using the Geometry object
        builder.setSites(pointsGeometry);

        // Extract edges from triangles
        return extractEdges(builder, extractor);
    }

    private List<CoordinateEdge> extractEdges(ConformingDelaunayTriangulationBuilder builder, PathsExtractor extractor) {
        List<CoordinateEdge> edges = new ArrayList<>();
        Map<EdgeNode, List<Point>> map = new HashMap<>();

        // Get the QuadEdgeSubdivision from the builder
        QuadEdgeSubdivision subdivision = builder.getSubdivision();

        // Iterate over the edges of the QuadEdgeSubdivision
        for (Object obj : subdivision.getPrimaryEdges(false)) {
            if (obj instanceof QuadEdge quadEdge) {
                Coordinate start = quadEdge.orig().getCoordinate();
                Coordinate end = quadEdge.dest().getCoordinate();
                edges.add(new CoordinateEdge(start, end));

                // Extract paths for start and end points
                List<List<Point>> paths = extractor.getEdgePaths(start, end);

                // Create MSTNode with new Point objects for source and target
                Point source = new Point((int) start.getX(), (int) start.getY());
                Point target = new Point((int) end.getX(), (int) end.getY());
                EdgeNode mstNode = new EdgeNode(source, target, paths.getFirst().size());

                // Add the path to the map with the edge node as the key
                map.put(mstNode, paths.getFirst()); // Assuming only one path is returned
            }
        }

        this.corridorsMap = map;

        return edges;
    }

    public Map<EdgeNode, List<Point>> getCorridorsMap() {
        return corridorsMap;
    }
}

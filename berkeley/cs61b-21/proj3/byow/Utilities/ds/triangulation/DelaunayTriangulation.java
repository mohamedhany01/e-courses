package byow.Utilities.ds.triangulation;

import byow.Core.world.Edge;
import byow.Utilities.ds.quadtree.Quadtree;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.ConformingDelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DelaunayTriangulation {
    public List<Edge> getEdges(LinkedList<Quadtree> dungeons) {
        List<CoordinateEdge> edgeList = performDelaunayTriangulation(dungeons);
        List<Edge> edges = new ArrayList<>();

        for (CoordinateEdge edge : edgeList) {
            edges.add(new Edge(edge));
        }

        return edges;
    }

    private List<CoordinateEdge> performDelaunayTriangulation(LinkedList<Quadtree> dungeons) {
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

        Geometry triangles = builder.getTriangles(new GeometryFactory());

        // Extract edges from triangles

        return extractEdges(builder);
    }

    private List<CoordinateEdge> extractEdges(ConformingDelaunayTriangulationBuilder builder) {
        List<CoordinateEdge> edges = new ArrayList<>();

        // Get the QuadEdgeSubdivision from the builder
        QuadEdgeSubdivision subdivision = builder.getSubdivision();

        // Iterate over the edges of the QuadEdgeSubdivision
        for (Object obj : subdivision.getPrimaryEdges(false)) {
            if (obj instanceof QuadEdge quadEdge) {
                Coordinate start = quadEdge.orig().getCoordinate();
                Coordinate end = quadEdge.dest().getCoordinate();
                edges.add(new CoordinateEdge(start, end));
            }
        }

        return edges;
    }
}

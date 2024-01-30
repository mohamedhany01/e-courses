package byow.Utilities.ds.graph;

import byow.Core.world.Edge;
import byow.Core.world.Point;
import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.ArrayList;
import java.util.List;

public class MST {
    public List<MSTNode> getMST(List<Edge> edges) {
        return generateMST(edges);
    }
    private List<MSTNode> generateMST(List<Edge> edges) {
        // Create a simple weighted graph
        Graph<Point, DefaultWeightedEdge> graph = GraphTypeBuilder
                .<Point, DefaultWeightedEdge>undirected()
                .allowingMultipleEdges(false)
                .allowingSelfLoops(false)
                .edgeClass(DefaultWeightedEdge.class)
                .weighted(true)
                .buildGraph();

        // Add vertices and edges to the graph
        for (Edge edge : edges) {
            Point p1 = edge.getStartPoint();
            Point p2 = edge.getEndPoint();
            double weight = edge.getWeight();

            if (!graph.containsVertex(p1)) {
                graph.addVertex(p1);
            }
            if (!graph.containsVertex(p2)) {
                graph.addVertex(p2);
            }

            DefaultWeightedEdge graphEdge = graph.addEdge(p1, p2);
            graph.setEdgeWeight(graphEdge, weight);
        }

        // Use Prim's algorithm to find the minimum spanning tree
        PrimMinimumSpanningTree<Point, DefaultWeightedEdge> primMST = new PrimMinimumSpanningTree<>(graph);

        // Get the minimum spanning tree
        Graph<Point, DefaultWeightedEdge> mstGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        List<MSTNode> MSTreeNodes = new ArrayList<>();

        for (DefaultWeightedEdge edge : primMST.getSpanningTree()) {
            Point source = graph.getEdgeSource(edge);
            Point target = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);

            if (!mstGraph.containsVertex(source)) {
                mstGraph.addVertex(source);
            }
            if (!mstGraph.containsVertex(target)) {
                mstGraph.addVertex(target);
            }

            DefaultWeightedEdge mstEdge = mstGraph.addEdge(source, target);
            mstGraph.setEdgeWeight(mstEdge, weight);

            MSTreeNodes.add(new MSTNode(source, target, (int)weight));

            // Debugging | Print MST edges to the console
//            System.out.println("MST Edge: " + source + " -> " + target + " | Weight: " + weight);
        }

        return MSTreeNodes;
    }
}

package org.example.dijkstra;

import org.example.graph.datastructure.AdjacencyList;
import org.example.graph.datastructure.AdjacencyMatrix;
import org.example.graph.extra.PathEntry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {
    public Dijkstra(AdjacencyMatrix graph, int sourceVertex) {
        scanMatrixForNegativeWeights(graph);
        findAllPathsForVertex(graph, sourceVertex);
    }

    public Dijkstra(AdjacencyList graph, int sourceVertex) {
//        scanMatrixForNegativeWeights(graph);
        findAllPathsForVertex(graph, sourceVertex);
    }

    private void scanMatrixForNegativeWeights(AdjacencyList list) {
        throw new UnsupportedOperationException("TODO");
    }

    private void scanMatrixForNegativeWeights(AdjacencyMatrix matrix) {
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.getMatrix()[i][j] < 0) {
                    throw new RuntimeException("Found " + matrix.getMatrix()[i][j] + ", Dijkstra's can't work negative weights.");
                }
            }
        }
    }

    private void initShortestPathsWithInfinity(int[] shortestPaths) {
        for (int i = 0; i < shortestPaths.length; i++) {
            shortestPaths[i] = Integer.MAX_VALUE; // Assuming infinity is MAX_VALUE
        }
    }

    private void findAllPathsForVertex(AdjacencyList list, int startVertex) {
        final PriorityQueue<PathEntry> pathsQueue = new PriorityQueue<>();
        final int[] adjacencyWeights = new int[list.getSize()];
        final HashMap<Integer, Integer> pathsTable = new HashMap<>();

        initShortestPathsWithInfinity(adjacencyWeights);

        adjacencyWeights[startVertex] = 0;

        pathsQueue.add(new PathEntry(startVertex, 0));
        pathsTable.put(startVertex, 0);

        while (!pathsQueue.isEmpty()) {
            int currentVertex = pathsQueue.poll().getIndex(); // Next vertex index to explore their adjacencies
            LinkedList<PathEntry> adjacencyList = list.getList().get(currentVertex);
            if (adjacencyList == null) {
                continue;
            }
            for (PathEntry entry : adjacencyList) {
                int adjacencyVertex = entry.getIndex();

                int adjacencyNewWeight = adjacencyWeights[currentVertex] + entry.getWeight();

                if (adjacencyNewWeight < adjacencyWeights[adjacencyVertex]) {
                    adjacencyWeights[adjacencyVertex] = adjacencyNewWeight;
                    pathsQueue.add(new PathEntry(adjacencyVertex, adjacencyNewWeight));

                    pathsTable.put(adjacencyVertex, adjacencyNewWeight);
                }
            }
        }

        printPathsTable(pathsTable);
    }

    private void findAllPathsForVertex(AdjacencyMatrix matrix, int startVertex) {
        final PriorityQueue<PathEntry> pathsQueue = new PriorityQueue<>();
        final int[] adjacencyWeights = new int[matrix.getSize()];
        final HashMap<Integer, Integer> pathsTable = new HashMap<>();

        initShortestPathsWithInfinity(adjacencyWeights);

        adjacencyWeights[startVertex] = 0;

        pathsQueue.add(new PathEntry(startVertex, 0));
        pathsTable.put(startVertex, 0);

        while (!pathsQueue.isEmpty()) {
            int currentVertex = pathsQueue.poll().getIndex(); // Next vertex index to explore their adjacencies

            // Scan all vertex adjacencies
            for (int adjacency = 0; adjacency < matrix.getSize(); adjacency++) {
                if (matrix.isConnected(currentVertex, adjacency)) {
                    int adjacencyNewWeight = adjacencyWeights[currentVertex] + matrix.getMatrix()[currentVertex][adjacency];

                    // Updating adjacency weight if we found a short one
                    if (adjacencyNewWeight < adjacencyWeights[adjacency]) {
                        adjacencyWeights[adjacency] = adjacencyNewWeight;

                        // Adding a new adjacency to the queue
                        pathsQueue.add(new PathEntry(adjacency, adjacencyNewWeight));

                        pathsTable.put(adjacency, adjacencyNewWeight);
                    }
                }
            }
        }

        printPathsTable(pathsTable);
    }

    private void printPathsTable(HashMap<Integer, Integer> pathsTable) {
        for (Map.Entry<Integer, Integer> table : pathsTable.entrySet()) {
            System.out.println(table.getKey() + " " + table.getValue());
        }
    }
}

package org.example.bellmanford.matrix;

import org.example.graph.datastructure.AdjacencyList;
import org.example.graph.datastructure.AdjacencyMatrix;
import org.example.graph.extra.PathEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord {
    public BellmanFord(AdjacencyMatrix graph, int sourceVertex) {
        findAllPathsForVertex(graph, sourceVertex);
    }

    public BellmanFord(AdjacencyList graph, int sourceVertex) {
        findAllPathsForVertex(graph, sourceVertex);
    }

    private void initShortestPathsWithInfinity(int[] shortestPaths) {
        for (int i = 0; i < shortestPaths.length; i++) {
            shortestPaths[i] = Integer.MAX_VALUE; // Assuming infinity is MAX_VALUE
        }
    }

    private void findAllPathsForVertex(AdjacencyMatrix graph, int sourceVertex) {
        final int[] adjacencyWeights = new int[graph.getSize()];
        final HashMap<Integer, Integer> pathsTable = new HashMap<>();
        int [] resultSnapshot = null;

        initShortestPathsWithInfinity(adjacencyWeights);

        adjacencyWeights[sourceVertex] = 0;

        pathsTable.put(sourceVertex, 0);

        for (int i = 0; i < graph.getSize() + 1; i++) {

            for (int j = 0; j < graph.getSize(); j++) {

                // Process only Weights with no Infinities (To be able to apply the algorithm on any vertex)
                if (adjacencyWeights[j] == Integer.MAX_VALUE) {
                    continue;
                }

                for (int k = 0; k < graph.getSize(); k++) {
                    if (graph.isConnected(j, k)) {
                        int adjacencyVertex = k;
                        int adjacencyNewWeight = adjacencyWeights[j] + graph.getMatrix()[j][adjacencyVertex];
                        if (adjacencyNewWeight < adjacencyWeights[adjacencyVertex]) {
                            adjacencyWeights[adjacencyVertex] = adjacencyNewWeight;
                            pathsTable.put(adjacencyVertex, adjacencyNewWeight);
                        }
                    }
                }
            }

            if (i == graph.getSize() - 1) {
                resultSnapshot = Arrays.copyOf(adjacencyWeights, adjacencyWeights.length);
            }

            if (i == graph.getSize()) {
                if (!Arrays.equals(resultSnapshot, adjacencyWeights)) {
                    System.out.println("WARNING: a negative loop is detected!");
                }
            }
        }

        printPathsTable(pathsTable);
    }

    private void findAllPathsForVertex(AdjacencyList graph, int sourceVertex) {
        final int[] adjacencyWeights = new int[graph.getSize()];
        final HashMap<Integer, Integer> pathsTable = new HashMap<>();
        int [] resultSnapshot = null;
        initShortestPathsWithInfinity(adjacencyWeights);

        adjacencyWeights[sourceVertex] = 0;
        pathsTable.put(sourceVertex, 0);

        for (int i = 0; i < graph.getSize() + 1; i++) {
            for (int j = 0; j < graph.getSize(); j++) {
                if (graph.getList().get(j) == null) {
                    continue;
                }

                // Process only Weights with no Infinities (To be able to apply the algorithm on any vertex)
                if (adjacencyWeights[j] == Integer.MAX_VALUE) {
                    continue;
                }

                for (PathEntry entry : graph.getList().get(j)) {
                    int adjacencyNewWeight = adjacencyWeights[j] + entry.getWeight();
                    int adjacencyVertex = entry.getIndex();
                    if (adjacencyNewWeight < adjacencyWeights[adjacencyVertex]) {
                        adjacencyWeights[adjacencyVertex] = adjacencyNewWeight;
                        pathsTable.put(adjacencyVertex, adjacencyNewWeight);

                        if (i == graph.getSize() - 1) {
                            resultSnapshot = Arrays.copyOf(adjacencyWeights, adjacencyWeights.length);
                        }

                        if (i == graph.getSize()) {
                            if (!Arrays.equals(resultSnapshot, adjacencyWeights)) {
                                System.out.println("WARNING: a negative loop is detected!");
                            }
                        }
                    }
                }
            }
            if (i == graph.getSize() - 1) {
                resultSnapshot = Arrays.copyOf(adjacencyWeights, adjacencyWeights.length);
            }

            if (i == graph.getSize()) {
                if (!Arrays.equals(resultSnapshot, adjacencyWeights)) {
                    System.out.println("WARNING: a negative loop is detected!");
                }
            }
        }

        printPathsTable(pathsTable);
    }

    private void printPathsTable(HashMap<Integer, Integer> pathsTable) {
        for (Map.Entry<Integer, Integer> table : pathsTable.entrySet()) {
            System.out.println(table.getKey() + " " + table.getValue());
        }
        System.out.println("==========================");
    }
}
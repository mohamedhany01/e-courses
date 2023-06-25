package org.example;

import org.example.bellmanford.matrix.BellmanFord;
import org.example.dijkstra.Dijkstra;
import org.example.graph.datastructure.AdjacencyList;
import org.example.graph.datastructure.AdjacencyMatrix;

public class Runner {
    public static void main(String[] args) {


//        // Directed graph
//        AdjacencyMatrix matrixDirected = new AdjacencyMatrix(7);
//        matrixDirected.connect(1, 2, 2);
//        matrixDirected.connect(1, 3, 4);
//        matrixDirected.connect(2, 3, 1);
//        matrixDirected.connect(2, 4, 7);
//        matrixDirected.connect(3, 5, 3);
//        matrixDirected.connect(4, 6, 1);
//        matrixDirected.connect(5, 4, 2);
//        matrixDirected.connect(5, 6, 5);
//
//        System.out.println("============ Dijkstra on directed graph ============");
//        Dijkstra dijkstraDirected  = new Dijkstra(matrixDirected, 1);
//        dijkstraDirected.printPathsTable();
//
//        System.out.println();
//
//        // Directed graph
//        AdjacencyMatrix matrixCyclicDirected = new AdjacencyMatrix(5);
//        matrixCyclicDirected.connect(0, 1, 14);
//        matrixCyclicDirected.connect(1, 2, 8);
//        matrixCyclicDirected.connect(2, 3, 2);
//        matrixCyclicDirected.connect(3, 2, 5);
//        matrixCyclicDirected.connect(3, 4, 9);
//
//
//        System.out.println("============ Dijkstra on cyclic directed graph ============");
//        Dijkstra dijkstraCyclicDirected  = new Dijkstra(matrixCyclicDirected, 0);
//        dijkstraCyclicDirected.printPathsTable();
//
//        System.out.println();
//
//        // Undirected graph
//        System.out.println("============ Dijkstra on undirected graph ============");
//        AdjacencyMatrix matrixUndirected = new AdjacencyMatrix(7);
//        matrixUndirected.connect(0, 1, 2);
//        matrixUndirected.connect(0, 2, 6);
//
//        matrixUndirected.connect(1, 0, 2);
//        matrixUndirected.connect(1, 3, 5);
//
//        matrixUndirected.connect(2, 0, 6);
//        matrixUndirected.connect(2, 3, 8);
//
//        matrixUndirected.connect(3, 1, 5);
//        matrixUndirected.connect(3, 2, 8);
//        matrixUndirected.connect(3, 4, 10);
//        matrixUndirected.connect(3, 5, 15);
//
//        matrixUndirected.connect(4, 3, 10);
//        matrixUndirected.connect(4, 5, 6);
//        matrixUndirected.connect(4, 6, 2);
//
//        matrixUndirected.connect(5, 3, 13);
//        matrixUndirected.connect(5, 4, 6);
//        matrixUndirected.connect(5, 6, 6);
//
//        matrixUndirected.connect(6, 4, 2);
//        matrixUndirected.connect(6, 5, 6);
//
//        Dijkstra dijkstraUndirected = new Dijkstra(matrixUndirected, 0);
//        dijkstraUndirected.printPathsTable();


        // BellmanFord
        System.out.println("==== BellmanFord ====");
        AdjacencyMatrix bellmanFordMatrix = new AdjacencyMatrix(8);
        AdjacencyList bellmanFordList = new AdjacencyList(8);

        bellmanFordMatrix.connect(1, 2, 6);
        bellmanFordMatrix.connect(1, 3, 5);
        bellmanFordMatrix.connect(1, 4, 5);

        bellmanFordMatrix.connect(2, 5, -1);

        bellmanFordMatrix.connect(3, 2, -2);
        bellmanFordMatrix.connect(3, 5, 1);

        bellmanFordMatrix.connect(4, 3, -2);
        bellmanFordMatrix.connect(4, 6, -1);

        bellmanFordMatrix.connect(5, 7, 3);

        bellmanFordMatrix.connect(6, 7, 3);

//        =========================

        bellmanFordList.connect(1, 2, 6);
        bellmanFordList.connect(1, 3, 5);
        bellmanFordList.connect(1, 4, 5);

        bellmanFordList.connect(2, 5, -1);

        bellmanFordList.connect(3, 2, -2);
        bellmanFordList.connect(3, 5, 1);

        bellmanFordList.connect(4, 3, -2);
        bellmanFordList.connect(4, 6, -1);

        bellmanFordList.connect(5, 7, 3);

        bellmanFordList.connect(6, 7, 3);
//
//        AdjacencyMatrix bellmanFordMatrix2 = new AdjacencyMatrix(5);
//        bellmanFordMatrix2.connect(1, 2, 4);
//        bellmanFordMatrix2.connect(1, 4, 5);
//        bellmanFordMatrix2.connect(2, 4, 5);
//        bellmanFordMatrix2.connect(3, 2, -10);
//        bellmanFordMatrix2.connect(4, 3, 3);

//        BellmanFord bellmanFordM = new BellmanFord(bellmanFordMatrix, 1);
//        BellmanFord bellmanFordL = new BellmanFord(bellmanFordList, 1);

        AdjacencyMatrix bellmanFordMatrixLoop = new AdjacencyMatrix(5);
        AdjacencyList bellmanFordListLoop = new AdjacencyList(5);

        bellmanFordMatrixLoop.connect(1, 2, 4);
        bellmanFordMatrixLoop.connect(1, 4, 5);
        bellmanFordMatrixLoop.connect(2, 4, 5);
        bellmanFordMatrixLoop.connect(3, 2, -10);
        bellmanFordMatrixLoop.connect(4, 3, 3);


        bellmanFordListLoop.connect(1, 2, 4);
        bellmanFordListLoop.connect(1, 4, 5);
        bellmanFordListLoop.connect(2, 4, 5);
        bellmanFordListLoop.connect(3, 2, -10);
        bellmanFordListLoop.connect(4, 3, 3);



        BellmanFord loop1 = new BellmanFord(bellmanFordMatrixLoop, 1);
        BellmanFord loop2 = new BellmanFord(bellmanFordListLoop, 1);
//        BellmanFord bellmanFord2 = new BellmanFord(bellmanFordMatrix2, 1);















        AdjacencyMatrix matrixDirected = new AdjacencyMatrix(7);
        matrixDirected.connect(1, 2, 2);
        matrixDirected.connect(1, 3, 4);

        matrixDirected.connect(2, 3, 1);
        matrixDirected.connect(2, 4, 7);

        matrixDirected.connect(3, 5, 3);

        matrixDirected.connect(4, 6, 1);

        matrixDirected.connect(5, 4, 2);
        matrixDirected.connect(5, 6, 5);

//        matrixDirected.printMatrix();
//        Dijkstra dijkstra1 = new Dijkstra(matrixDirected, 1);

//        System.out.println("==============================");
        AdjacencyList adjacencyList = new AdjacencyList(7);

        adjacencyList.connect(1, 2, 2);
        adjacencyList.connect(1, 3, 4);

        adjacencyList.connect(2, 3, 1);
        adjacencyList.connect(2, 4, 7);

        adjacencyList.connect(3, 5, 3);

        adjacencyList.connect(4, 6, 1);

        adjacencyList.connect(5, 4, 2);
        adjacencyList.connect(5, 6, 5);

//        adjacencyList.printList();

//        Dijkstra dijkstra2 = new Dijkstra(adjacencyList, 1);

    }
}

package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void print(char[][] graph) {
        System.out.print("\t");
        for (int j = 0; j < graph.length; j++) {
            System.out.print(j + " | ");
        }
        System.out.println();
        for (int i = 0; i < graph.length; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j] + " | ");
            }
            System.out.println();
        }
    }

    public static void initGraph(char[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                graph[i][j] = 'x';
            }
        }
    }

    public static void connect(char[][] graph, int n1, int n2) {
        graph[n1][n2] = '1';
    }

    public static boolean isConnected(char[][] graph, int n1, int n2) {
        return graph[n1][n2] == '1';
    }

    public static void bfs(char[][] graph, int startPoint) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.length];

        queue.add(startPoint);
        visited[startPoint] = true;
        while (!queue.isEmpty()) {
            int popOut = queue.remove();
            System.out.print(popOut + " ");
            for (int i = 0; i < graph.length; i++) {
                if (graph[popOut][i] == '1' && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }

        System.out.println();
    }


    public static void dfs(char[][] graph, int startPoint) {
        boolean[] visited = new boolean[graph.length];
        dfsRecursively(graph, startPoint, visited);
        System.out.println();
    }

    public static void dfsRecursively(char[][] graph, int startPoint, boolean[] visited) {
        if (!visited[startPoint]) {
            visited[startPoint] = true;
            System.out.print(startPoint + " ");

            // Star exploring other vertices connected to the start point
            for (int i = 0; i < graph.length; i++) {
                if (graph[startPoint][i] == '1' && !visited[i]) {
                    dfsRecursively(graph, i, visited);
                }
            }
        }
    }

    public static void dfsBackward(char[][] graph, int startPoint) {
        boolean[] visited = new boolean[graph.length];
        dfsRecursivelyBackward(graph, startPoint, visited);
        System.out.println();
    }

    private static void dfsRecursivelyBackward(char[][] graph, int startPoint, boolean[] visited) {
        if (!visited[startPoint]) {
            visited[startPoint] = true;
            System.out.print(startPoint + " ");

            // Star exploring other vertices connected to the start point
            for (int i = graph.length - 1; i >= 0; i--) {
                if (graph[startPoint][i] == '1' && !visited[i]) {
                    dfsRecursively(graph, i, visited);
                }
            }
        }
    }

    public static boolean isCyclicalUtil(char[][] graph, int v, boolean[] visited, boolean[] recursionStack) {
        if (!visited[v]) {
            visited[v] = true;
            recursionStack[v] = true;
            for (int i = 0; i < graph.length; i++) {
                if (graph[v][i] == '1') {
                    boolean x = !visited[i];
                    if (x && isCyclicalUtil(graph, i, visited, recursionStack)) {
                        return true;
                    } else if (recursionStack[i]) {
                        return true;
                    }
                }
            }
        }
        recursionStack[v] = false;
        return false;
    }

    public static boolean isCyclical(char[][] graph) {
        int numVertices = graph.length;
        boolean[] visited = new boolean[numVertices];
        boolean[] recursionStack = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (isCyclicalUtil(graph, i, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        char[][] graph = new char[8][8];

        initGraph(graph);

        connect(graph, 1, 2);
        connect(graph, 1, 3);
        connect(graph, 1, 4);

        connect(graph, 2, 3);
        connect(graph, 2, 1);

        connect(graph, 3, 1);
        connect(graph, 3, 2);
        connect(graph, 3, 4);
        connect(graph, 3, 5);

        connect(graph, 4, 1);
        connect(graph, 4, 3);
        connect(graph, 4, 5);

        connect(graph, 5, 3);
        connect(graph, 5, 4);
        connect(graph, 5, 6);
        connect(graph, 5, 7);

        connect(graph, 6, 5);
        connect(graph, 6, 7);
//
//        System.out.println("-------- BFS Search --------");
//        bfs(graph, 1);
//
//        System.out.println("-------- DFS Search --------");
        dfs(graph, 1);
//        dfsBackward(graph, 1);

//        char[][] cyclicGraph = new char[3][3];
//        initGraph(cyclicGraph);
//        connect(cyclicGraph, 0, 1);
//        connect(cyclicGraph, 1, 2);
//        connect(cyclicGraph, 2, 0);
//        System.out.println(isCyclical(cyclicGraph));

//        char[][] cyclicGraph = new char[5][5];
//        initGraph(cyclicGraph);
//        connect(cyclicGraph, 0, 1);
//        connect(cyclicGraph, 2, 1);
//        connect(cyclicGraph, 2, 3);
//        connect(cyclicGraph, 3, 4);
//        connect(cyclicGraph, 4, 0);
//        connect(cyclicGraph, 4, 2);
//        System.out.println(isCyclical(cyclicGraph));

//        char[][] cyclicGraph2 = new char[6][6];
//        initGraph(cyclicGraph2);
//        connect(cyclicGraph2, 0, 1);
//        connect(cyclicGraph2, 1, 2);
//        connect(cyclicGraph2, 2, 3);
//        connect(cyclicGraph2, 3, 4);
//        connect(cyclicGraph2, 3, 5);
//        connect(cyclicGraph2, 4, 1);
//
//        System.out.println(isCyclical(cyclicGraph2));

//        char[][] cyclicGraph3 = new char[3][3];
//        initGraph(cyclicGraph3);
//        connect(cyclicGraph3, 0, 1);
//        connect(cyclicGraph3, 1, 2);
//        connect(cyclicGraph3, 2, 0);
//
//        System.out.println(isCyclical(cyclicGraph3));

//        char[][] acyclicGraph = new char[3][3];
//        initGraph(acyclicGraph);
//        connect(acyclicGraph, 0, 1);
//        connect(acyclicGraph, 0, 2);
//        connect(acyclicGraph, 1, 2);
//        print(acyclicGraph);
//
//        System.out.println(isCyclical(acyclicGraph));
    }
}
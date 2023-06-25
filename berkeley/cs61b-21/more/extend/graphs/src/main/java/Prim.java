import java.util.*;
import java.util.stream.IntStream;
import edu.princeton.cs.algorithms.QuickUnionUF;
import edu.princeton.cs.algorithms.WeightedQuickUnionUF;

public class Prim {
    public static void main(String[] args) {

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(20);
        uf.connected(2, 4);
        uf.connected(2, 1);
        QuickUnionUF quickUnionUF = new QuickUnionUF(100);
//        quickUnionUF.

        System.out.println(uf.count());
        int[][] graph = new int[9][9];
        initGraph(graph);
        // GoG
        connect(graph, 0, 1, 4);
        connect(graph, 0, 7, 8);

        connect(graph, 1, 2, 8);
        connect(graph, 1, 7, 11);

        connect(graph, 2, 3, 7);
        connect(graph, 2, 5, 4);
        connect(graph, 2, 8, 2);

        connect(graph, 3, 4, 9);
        connect(graph, 3, 5, 14);

        connect(graph, 5, 4, 10);
        connect(graph, 5, 6, 2);

        connect(graph, 6, 7, 1);
        connect(graph, 6, 8, 6);

        connect(graph, 7, 6, 1);
        connect(graph, 7, 8, 7);

        prim(graph, 0);


        // https://youtu.be/xthRL0lcx2w?t=261
//        int[][] graph = new int[4][4];
//        initGraph(graph);
//        connect(graph, 0, 1, 3);
//        connect(graph, 0, 2, 1);
//
//        connect(graph, 1, 2, 2);
//        connect(graph, 1, 3, 4);
//
//        connect(graph, 2, 3, 5);

//        prim(graph, 0);


    // Abdul Bari https://www.youtube.com/watch?v=4ZlRH0eK-qQ
//        int[][] graph = new int[8][8];
//        initGraph(graph);
//
//        connect(graph, 1, 2, 28);
//        connect(graph, 1, 6, 10);
//
//        connect(graph, 2, 3, 16);
//        connect(graph, 2, 7, 14);
//
//        connect(graph, 3, 4, 12);
//
//        connect(graph, 5, 6, 25);
//        connect(graph, 5, 7, 24);
//        connect(graph, 5, 4, 22);
//
//        connect(graph, 7, 4, 18);

//        prim(graph, 1);

//        print(graph, false);

    }

    private static void prim(int[][] graph, int source) {
        class Entry implements Comparable<Entry> {
            final int index;
            final int weight;

            public Entry(int index, int weight) {
                this.index = index;
                this.weight = weight;
            }

            @Override
            public int compareTo(Entry o) {
                return this.weight - o.weight;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Entry entry = (Entry) o;
                return index == entry.index;
            }

            @Override
            public int hashCode() {
                return Objects.hash(index);
            }
        }
        PriorityQueue<Entry> priorityQueue = new PriorityQueue<>();

        int[] distTo = new int[graph.length];
        boolean[] mstSet = new boolean[graph.length];
        HashMap<Integer, Integer> edgeTo = new HashMap<>();

        IntStream.range(0, distTo.length).forEach(i -> distTo[i] = Integer.MAX_VALUE);

        distTo[source] = 0;
        mstSet[source] = true;
        edgeTo.put(source, -1);

        priorityQueue.add(new Entry(source, 0));
        while (!priorityQueue.isEmpty()) {
            int vertex = priorityQueue.poll().index;

            for (int i = 0; i < graph.length; i++) {
                if (isConnected(graph, vertex, i)) {
                    int newWeight = getWeight(graph, vertex, i);
                    if (mstSet[i]) continue;
                    if (newWeight < distTo[i]) {
                        distTo[i] = newWeight;
                        edgeTo.put(i, vertex);

                        Entry entry = new Entry(i, newWeight);
                        if (priorityQueue.contains(entry)) {
                            priorityQueue.remove(entry);
                            priorityQueue.add(entry);
                        } else {
                            priorityQueue.add(new Entry(i, newWeight));
                        }
                    }
                }
            }
            mstSet[vertex] = true;
        }

        System.out.println(edgeTo);
        int total = 0;
        for (int i = 0; i < distTo.length; i++) {
            if (distTo[i] != Integer.MAX_VALUE) {
                total += distTo[i];
            }
        }
        System.out.println(total);
    }

    private static int getWeight(int[][] graph, int vertex, int i) {
        return graph[vertex][i];
    }


    public static void initGraph(int[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public static void connect(int[][] graph, int n1, int n2, int weight) {
        graph[n1][n2] = weight;
        graph[n2][n1] = weight;
    }

    public static boolean isConnected(int[][] graph, int n1, int n2) {
        return graph[n1][n2] != Integer.MAX_VALUE;
    }

    public static void print(int[][] graph, boolean letter) {
        System.out.print("\t");
        if (letter) {
            for (int j = 97; j < 97 + graph.length; j++) {
                System.out.write(j);
                System.out.print(" | ");
            }
        } else {
            for (int j = 0; j < graph.length; j++) {
                System.out.print(j + " | ");
            }
        }
        System.out.println();
        for (int i = 0; i < graph.length; i++) {
            if (letter) {
                System.out.write(97 + i);
                System.out.print(" | ");

            } else {
                System.out.print(i + " | ");
            }
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j] + " | ");
            }
            System.out.println();
        }
    }
}

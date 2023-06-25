package org.example.kruskal;

import edu.princeton.cs.algorithms.QuickUnionUF;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class KruskalRun {
    public static void main(String[] args) {
        UndirectedMatrix matrix = new UndirectedMatrix(9);


        matrix.connect(0, 1, 4);
        matrix.connect( 0, 7, 8);

        matrix.connect( 1, 2, 8);
        matrix.connect( 1, 7, 11);

        matrix.connect( 2, 3, 7);
        matrix.connect( 2, 5, 4);
        matrix.connect(2, 8, 2);

        matrix.connect( 3, 4, 9);
        matrix.connect( 3, 5, 14);

        matrix.connect( 5, 4, 10);
        matrix.connect( 5, 6, 2);

        matrix.connect( 6, 7, 1);
        matrix.connect( 6, 8, 6);

        matrix.connect( 7, 6, 1);
        matrix.connect( 7, 8, 7);

        kruskal(matrix);
    }

    public static void kruskal(UndirectedMatrix matrix) {
        PriorityQueue<Entry> queue = new PriorityQueue<>();
        boolean [] visited = new boolean[matrix.getSize()];
        QuickUnionUF uf = new QuickUnionUF(matrix.getSize());
        List<Entry> MST = new LinkedList<>();

        SortedList sortedList = new SortedList(matrix);
        List<Entry> list = sortedList.getSorted();

        for (Entry e:list) {
//            System.out.println(e.a + " " + e.b + " " + e.weight);
            if (!uf.connected(e.a, e.b)) {
                uf.union(e.a, e.b);
                MST.add(new Entry(e.a, e.b, e.weight));
            }
        }

        int t = 0;
        for (Entry e:MST) {
                        System.out.println(e.a + " " + e.b + " " + e.weight);
                        t += e.weight;

        }
        System.out.println(t);
//        for (int i = 0; i < list.size(); i++) {
//
//        }

    }

}

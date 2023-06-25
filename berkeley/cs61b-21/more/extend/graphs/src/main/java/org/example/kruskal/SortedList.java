package org.example.kruskal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SortedList {
    List<Entry> list;
    UndirectedMatrix graph;
    public SortedList(UndirectedMatrix graph) {
        this.graph = graph;
        this.list = new LinkedList<>();
        setEntries(graph);
    }

    private void setEntries(UndirectedMatrix graph) {
        boolean [] visited = new boolean[graph.getSize()];

        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = 0; j < graph.getSize(); j++) {
                if (visited[j]) continue;
                if (graph.isConnected(i, j)) {
                    list.add(new Entry(i, j, graph.getWeight(i, j)));
                }
            }
            visited[i] = true;
        }
    }


    public List<Entry> getSorted() {
        Collections.sort(this.list);
        return this.list;
    }
}

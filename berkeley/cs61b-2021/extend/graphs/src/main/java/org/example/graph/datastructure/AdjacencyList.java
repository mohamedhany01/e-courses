package org.example.graph.datastructure;

import org.example.graph.extra.PathEntry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AdjacencyList {
    private HashMap<Integer, LinkedList<PathEntry>> list;
    private int size;

    public AdjacencyList(int size) {
        this.list = new HashMap<>();
        this.size = size;
    }

    public boolean isConnected(int a, int b) {
        if (a >= size || b >= size) {
            throw new RuntimeException("Out of size boundaries");
        }

        if (this.list.get(a) != null) {
            LinkedList<PathEntry> pathsList = this.list.get(a);
            for (PathEntry pathEntry : pathsList) {
                if (pathEntry.getIndex() == b) {
                    return true;
                }
            }

        }
        return false;

    }

    public void connect(int a, int b, int weight) {
        if (a >= size || b >= size) {
            throw new RuntimeException("Out of size boundaries");
        }

        if (this.list.get(a) == null) {
            LinkedList<PathEntry> container = new LinkedList<>();
            PathEntry entry = new PathEntry(b, weight);
            container.add(entry);

            this.list.put(a, container);
        } else {
            LinkedList<PathEntry> container = this.list.get(a);
            container.add(new PathEntry(b, weight));
            this.list.put(a, container);
        }
    }

    public void disconnect(int a, int b) {
        if (a >= size || b >= size) {
            throw new RuntimeException("Out of size boundaries");
        }

        if (this.list.get(a) == null) {
            throw new RuntimeException(a + " isn't exist");
        } else {
            LinkedList<PathEntry> pathsList = this.list.get(a);
            for (PathEntry entry : pathsList) {
                if (entry.getIndex() == b) {
                    pathsList.remove(entry);
                    if (this.list.get(a).size() == 0) {
                        this.list.remove(a);
                    } else {
                        this.list.put(a, pathsList);
                    }
                }
            }
        }
    }


    public int getSize() {
        return this.size;
    }

    public void printList() {
        for (Map.Entry<Integer, LinkedList<PathEntry>> map : this.list.entrySet()) {
            int key = map.getKey();
            LinkedList<PathEntry> entryLinkedList = map.getValue();
            System.out.print(key + " => ( ");
            for (PathEntry entry : entryLinkedList) {
                System.out.print("[" + entry.getIndex() + ", " + entry.getWeight() + "] ");
            }
            System.out.println(")");
        }

        System.out.println();
    }

    public HashMap<Integer, LinkedList<PathEntry>> getList() {
        return this.list;
    }
}

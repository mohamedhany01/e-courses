package org.example.kruskal;

import java.util.Objects;

public class Entry implements Comparable<Entry> {
    final int a;
    final int b;
    final int weight;

    public Entry(int a, int b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    @Override
    public int compareTo(Entry o) {
        return this.weight - o.weight;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getWeight() {
        return weight;
    }
}

package org.example.graph.extra;

public class PathEntry implements Comparable<PathEntry> {
    private final Integer index;
    private final Integer weight;

    public PathEntry(Integer index, Integer weight) {
        this.index = index;
        this.weight = weight;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getWeight() {
        return weight;
    }


    @Override
    public int compareTo(PathEntry o) {
        return this.weight.compareTo(o.weight);
    }
}

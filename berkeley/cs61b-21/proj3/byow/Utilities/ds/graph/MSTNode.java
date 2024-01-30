package byow.Utilities.ds.graph;

import byow.Core.world.Point;

public class MSTNode {
    private final Point source;
    private final Point target;
    private final int weight;

    public MSTNode(Point source, Point target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Point getSource() {
        return source;
    }

    public Point getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "MSTGraph{" +
                "source=" + source +
                ", target=" + target +
                ", weight=" + weight +
                '}';
    }
}

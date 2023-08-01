package org.huffman;

public class Node implements Comparable<Node> {
    private Node leftNode;
    private Node rightNode;
    private Integer frequency;
    private Character letter;

    public Node(Node leftNode, Node rightNode, Integer frequency, Character letter) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.frequency = frequency;
        this.letter = letter;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Character getLetter() {
        return letter;
    }

    public boolean isLeaf() {
        return this.leftNode == null && this.rightNode == null;
    }

    @Override
    public int compareTo(Node other) {
        // Compare based on a frequency
        final Integer frequencyResult = Integer.compare(this.frequency, other.frequency);

        if (frequencyResult != 0) {
            return frequencyResult;
        }

        // Else based on a letter
        return Integer.compare(this.letter, other.letter);
    }

    @Override
    public String toString() {
        return "Node{" +
                "leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                ", frequency=" + frequency +
                ", letter=" + letter +
                '}';
    }
}

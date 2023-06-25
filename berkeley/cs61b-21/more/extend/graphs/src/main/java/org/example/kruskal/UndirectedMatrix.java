package org.example.kruskal;

public class UndirectedMatrix {
    private final int[][] matrix;
    private final int size;

    public int[][] getMatrix() {
        return matrix;
    }

    public UndirectedMatrix(int size) {
        this.size = size;
        matrix = new int[this.size][this.size];
    }

    public void connect(int a, int b, int weight) {
        matrix[a][b] = weight;
        matrix[b][a] = weight;
    }

    public boolean isConnected(int a, int b) {
        return matrix[a][b] != 0 || matrix[b][a] != 0;
    }

    public int getWeight(int a, int b) {
        return matrix[a][b];
    }

    public int getSize() {
        return size;
    }
}

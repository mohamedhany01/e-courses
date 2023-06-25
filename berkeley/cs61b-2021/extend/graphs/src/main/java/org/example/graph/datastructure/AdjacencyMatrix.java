package org.example.graph.datastructure;

public class AdjacencyMatrix {
    private int size;
    private int[][] matrix;

    public AdjacencyMatrix(int size) {
        this.size = size;
        this.matrix = new int[this.size][this.size];
    }

    public void connect(int a, int b, int weight) {
        this.matrix[a][b] = weight;
    }

    public boolean isConnected(int a, int b) {
        return this.matrix[a][b] != 0;
    }

    public void disconnect(int a, int b) {
        this.matrix[a][b] = 0;
    }

    public void printMatrix() {
        System.out.print("\t");
        for (int j = 0; j < this.matrix.length; j++) {
            System.out.print(j + " | ");
        }
        System.out.println();
        for (int i = 0; i < this.matrix.length; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < this.matrix.length; j++) {
                System.out.print(this.matrix[i][j] + " | ");
            }
            System.out.println();
        }
    }

    public int getSize() {
        return this.size;
    }

    public int[][] getMatrix() {
        return this.matrix;
    }
}

package heap;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Heap<T extends Comparable<T>> {
    protected static final int ROOT = 0;
    private static final int DEFAULT_SIZE = 10;
    protected T[] heaps;
    protected int size;

    public Heap() {
        this.heaps = (T[]) Array.newInstance(Comparable.class, DEFAULT_SIZE);
        this.size = 0;
    }

    public Heap(int heapSize) {
        this.heaps = (T[]) Array.newInstance(Comparable.class, heapSize);
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public int find(T kay) {
        for (int i = 0; i < getSize(); i++) {
            if (this.heaps[i].equals(kay)) {
                return i;
            }
        }
        return -1;
    }

    public T getMaxHeap() {
        if (isEmpty()) {
            return null;
        }

        return this.heaps[ROOT];
    }

    protected int getLeft(int heapIndex) {
        return (2 * heapIndex) + 1;
    }

    protected int getRight(int heapIndex) {
        return (2 * heapIndex) + 2;
    }

    protected int getParent(int heapIndex) {
        return (heapIndex - 1) / 2;
    }

    protected void swap(T[] heap, int a, int b) {
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "heaps=" + Arrays.toString(heaps) +
                '}';
    }
}

package app;

import heap.MaxHeap;
import heap.MinHeap;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("================= MAX HEAP =================");

        MaxHeap<Integer> maxHeap = new MaxHeap<>();

        maxHeap.add(50);
        maxHeap.add(30);
        maxHeap.add(20);
        maxHeap.add(15);
        maxHeap.add(10);
        maxHeap.add(1000);
        maxHeap.add(8);
        maxHeap.add(16);

        System.out.println(maxHeap);
        System.out.println("Find 1000: " + maxHeap.find(1000));
        System.out.println("Max Heap: " + maxHeap.getMaxHeap());

        int[] sortedArrayMax = new int[maxHeap.getSize()];
        for (int i = maxHeap.getSize() - 1; i >= 0; i--) {
            sortedArrayMax[i] = maxHeap.removeMax();
        }

        System.out.println(Arrays.toString(sortedArrayMax));
        System.out.println("Is empty: " + maxHeap.isEmpty());

        System.out.println(Arrays.toString(maxHeap.heapify(new Integer[]{50, 20, 10, 30, 1000, 16, 15, 8})));

        System.out.println("================= MIN HEAP =================");
        MinHeap<Integer> minHeap = new MinHeap<>();

        minHeap.add(50);
        minHeap.add(30);
        minHeap.add(20);
        minHeap.add(15);
        minHeap.add(10);
        minHeap.add(1000);
        minHeap.add(8);
        minHeap.add(16);

        System.out.println(minHeap);
        System.out.println("Find 1000: " + minHeap.find(1000));
        System.out.println("Min Heap: " + minHeap.getMaxHeap());

        int[] sortedArrayMin = new int[minHeap.getSize()];
        for (int i = minHeap.getSize() - 1; i >= 0; i--) {
            sortedArrayMin[i] = minHeap.removeMin();
        }

        System.out.println(Arrays.toString(sortedArrayMin));
        System.out.println("Is empty: " + minHeap.isEmpty());

        System.out.println(Arrays.toString(minHeap.heapify(new Integer[]{50, 20, 10, 30, 1000, 16, 15, 8})));
    }
}
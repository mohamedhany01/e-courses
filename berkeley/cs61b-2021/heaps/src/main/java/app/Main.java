package app;

import heap.MaxHeap;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
//            maxHeap.add(10);
//        maxHeap.add(20);
//        maxHeap.add(100);
//        maxHeap.add(1000);
//        maxHeap.add(200);

        maxHeap.add(66);
//        System.out.println(maxHeap.removeMax());
        maxHeap.add(50);
        maxHeap.add(30);
        maxHeap.add(20);
        maxHeap.add(15);
        maxHeap.add(10);
        maxHeap.add(8);
        maxHeap.add(16);

        System.out.println(maxHeap.find(16));

        maxHeap.increaseKey(50, 70);
        maxHeap.increaseKey(8, 80);
        int[] b = new int[maxHeap.getSize()];
        for (int i = maxHeap.getSize() - 1; i >= 0; i--) {
            b[i] = maxHeap.removeMax();
        }

        System.out.println(Arrays.toString(b));

        System.out.println(Arrays.toString(maxHeap.heapify(new Integer[]{8, 16, 20, 30, 50, 10, 15})));

        System.out.println(Arrays.toString(maxHeap.heapify(new Integer[]{10, 20, 15, 12, 40, 25, 18})));
    }
}
package org.sort.heap;

import org.sort.Sortable;

import java.util.Arrays;

public class HeapSort implements Sortable {
    private Integer[] array;
    private final MaxHeap<Integer> maxHeap;

    public HeapSort(Integer[] array) {
        maxHeap = new MaxHeap<>(array);
    }

    @Override
    public Integer[] sort() {
        this.array = maxHeap.sort();
        return this.array;
    }

    @Override
    public void show() {
        System.out.println("\nHeap Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}

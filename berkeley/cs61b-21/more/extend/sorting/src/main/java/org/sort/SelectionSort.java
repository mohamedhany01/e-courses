package org.sort;

import java.util.Arrays;

public class SelectionSort implements Sortable {
    private final Integer[] array;

    public SelectionSort(Integer[] array) {
        this.array = array;
    }

    private void swap(Integer[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    @Override
    public Integer[] sort() {
        if (this.array.length == 0 || this.array.length == 1) {
            return this.array;
        }

        for (int i = 0; i < this.array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < this.array.length; j++) {
                if (this.array[j] < this.array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(this.array, i, minIndex);
        }

        return this.array;
    }

    @Override
    public void show() {
        System.out.println("\nSelection Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}
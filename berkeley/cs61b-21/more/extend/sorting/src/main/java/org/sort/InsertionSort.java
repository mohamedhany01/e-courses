package org.sort;

import java.util.Arrays;

public class InsertionSort implements Sortable {
    private final Integer[] array;

    public InsertionSort(Integer[] array) {
        this.array = array;
    }


    @Override
    public Integer[] sort() {
        int leftPointer = 0;
        for (int i = 1; i < this.array.length; i++) {
            if (array[leftPointer] > array[i]) {
                shift(this.array, i);
            }
            leftPointer++;
        }
        return this.array;
    }

    private void shift(Integer[] array, int i) {
        int temp = array[i];

        for (int j = i; j > 0; j--) {
            int previous = j - 1;
            if (temp < array[previous]) {
                array[j] = array[previous];
                array[previous] = temp;
            }
        }
    }

    @Override
    public void show() {
        System.out.println("\nInsertion Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}

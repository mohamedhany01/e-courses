package org.sort;

import java.util.Arrays;
import java.util.Random;

//  Quicksort Sort Algorithm in Java - Full Tutorial With Source  || https://www.youtube.com/watch?v=h8eyY7dIiN4
public class QuickSort implements Sortable {
    private final Integer[] array;

    public QuickSort(Integer[] array) {
        this.array = array;
    }

    private void QSort(Integer[] array, int leftIndex, int rightIndex) {

        // Base condition
        if (leftIndex >= rightIndex) {
            return;
        }

        int pivotIndex = new Random().nextInt(rightIndex - leftIndex) + leftIndex;
        int pivot = array[pivotIndex];

        swap(array, pivotIndex, rightIndex);

        int leftPointer = partition(array, leftIndex, rightIndex, pivot);

        QSort(array, leftIndex, leftPointer - 1);
        QSort(array, leftPointer + 1, rightIndex);
    }

    private int partition(Integer[] array, int leftIndex, int rightIndex, int pivot) {
        int leftPointer = leftIndex;
        int rightPointer = rightIndex;

        while (leftPointer < rightPointer) {

            // Find a number higher than the pivot
            while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }

            // Find a number less than the pivot
            while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }

            swap(array, leftPointer, rightPointer);
        }

        swap(array, leftPointer, rightIndex);

        return leftPointer;
    }

    @Override
    public Integer[] sort() {
        QSort(this.array, 0, this.array.length - 1);
        return this.array;
    }

    private void swap(Integer[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    @Override
    public void show() {
        System.out.println("\nQuick Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}

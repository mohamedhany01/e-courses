package org.sort;

import java.util.Arrays;

public class MergeSort implements Sortable {
    private final Integer[] array;

    public MergeSort(Integer[] array) {
        this.array = array;
    }

    private void MSort(Integer[] array) {
        int arrayLength = array.length;

        if (arrayLength < 2) {
            return;
        }

        int middle = array.length / 2;
        Integer[] leftArray = new Integer[middle];
        Integer[] rightArray = new Integer[arrayLength - middle];

        System.arraycopy(array, 0, leftArray, 0, middle);

        if (arrayLength - middle >= 0)
            System.arraycopy(array, middle, rightArray, 0, arrayLength - middle);

        MSort(leftArray);
        MSort(rightArray);

        merge(array, leftArray, rightArray);
    }

    private void merge(Integer[] array, Integer[] leftArray, Integer[] rightArray) {
        int leftLength = leftArray.length;
        int rightLength = rightArray.length;

        int l, r, a;
        l = r = a = 0;

        // If both arrays have the same length
        while (l < leftLength && r < rightLength) {

            if (leftArray[l] <= rightArray[r]) {
                array[a] = leftArray[l];
                l++;
            } else {
                array[a] = rightArray[r];
                r++;
            }
            a++;
        }

        // If length one of the arrays is bigger than the other
        while (l < leftLength) {
            array[a] = leftArray[l];
            l++;
            a++;
        }

        while (r < rightLength) {
            array[a] = rightArray[r];
            r++;
            a++;
        }
    }

    @Override
    public Integer[] sort() {
        MSort(this.array);
        return this.array;
    }

    @Override
    public void show() {
        System.out.println("\nMerge Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}

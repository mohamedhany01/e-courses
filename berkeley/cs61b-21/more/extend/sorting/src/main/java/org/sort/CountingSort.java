package org.sort;

import java.util.Arrays;
import java.util.HashMap;

public class CountingSort implements Sortable {
    private Integer[] array;

    public CountingSort(Integer[] array) {
        this.array = array;
    }

    @Override
    public Integer[] sort() {
        int length = this.array.length;

        if (length == 0 || length == 1) {
            return this.array;
        }

        int max = Arrays.stream(this.array).max(Integer::compareTo).get();
        HashMap<Integer, Integer> occurrence = new HashMap<>();

        // Init the occurrence table with ZEROs
        for (int i = 0; i < max + 1; i++) {
            occurrence.put(i, 0);
        }

        // Count number of occurrence
        for (int i = 0; i < length; i++) {
            int occurrenceOnTable = this.array[i];
            if (occurrence.containsKey(occurrenceOnTable)) {
                int newValue = occurrence.get(occurrenceOnTable) + 1;
                occurrence.put(occurrenceOnTable, newValue);
            }
        }

        // Sum cumulatively
        int cumulative = occurrence.get(0);
        for (int i = 1; i < max + 1; i++) {
            occurrence.put(i, cumulative + occurrence.get(i));
            cumulative = occurrence.get(i);
        }

        // Start sorting using info in occurrence table
        Integer[] newSortedArray = new Integer[length];
        for (int i = 0; i < length; i++) {
            int arrayIndex = this.array[i];
            occurrence.put(arrayIndex, occurrence.get(arrayIndex) - 1);
            newSortedArray[occurrence.get(arrayIndex)] = arrayIndex;
        }

        this.array = newSortedArray;

        return this.array;
    }

    @Override
    public void show() {
        System.out.println("\nCounting Sort\n");
        System.out.println(Arrays.toString(this.array));
        System.out.println("\n============");
    }
}

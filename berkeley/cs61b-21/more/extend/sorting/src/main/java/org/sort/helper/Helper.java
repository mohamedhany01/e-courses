package org.sort.helper;

import java.util.Random;

public class Helper {
    private Integer[] array;

    public Helper() {
    }

    public Integer[] getRandomArrayOfSize(int size, int bound) {
        Random random = new Random();
        this.array = new Integer[size];

        for (int i = 0; i < size; i++) {
            this.array[i] = random.nextInt(bound);
        }

        return array;
    }
}

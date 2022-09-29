package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }

        // Assume the index of "0" is the max
        T maxInList = get(0);

        for (int i = 0; i < getLength(); i++) {
            T itemInList = get(i);

            /*
            * Skip comparing if null is found,
            * because my logic is considering find a null in anyplace in the list.
            * SEE: my logic for the pointers "front/back" in Deque.java
            * */
            if (itemInList == null) {
                continue;
            }

            if (comparator.compare(maxInList, itemInList) > 0) {
                maxInList = itemInList;
            }
        }
        return maxInList;
    }

    public T max(Comparator<T> com) {
        if (isEmpty()) {
            return null;
        }

        // Assume the index of "0" is the max
        T maxInList = get(0);

        for (int i = 0; i < getLength(); i++) {
            T itemInList = get(i);

            /*
             * Skip comparing if null is found,
             * because my logic is considering find a null in anyplace in the list.
             * SEE: my logic for the pointers "front/back" in Deque.java
             * */
            if (itemInList == null) {
                continue;
            }

            if (com.compare(maxInList, itemInList) > 0) {
                maxInList = itemInList;
            }
        }
        return maxInList;
    }
}

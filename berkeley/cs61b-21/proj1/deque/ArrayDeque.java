package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private static final int INT_SIZE = 8;
    private final int FACTOR = 2;
    private T[] list;
    private int front, back;
    private int size;

    public ArrayDeque() {
        list = (T[]) new Object[INT_SIZE];
        front = back = -1;
        size = 0;
    }

    public ArrayDeque(int size) {
        list = (T[]) new Object[size];
        front = back = -1;
        this.size = 0;
    }

    public boolean isFull() {
//        return (((back + 1) % list.length) == front);
        return (size == list.length);
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(size * FACTOR);
        }

        if (isEmpty()) {
            front = back = 0;
        } else if (front == 0) {
            front = list.length - 1;
        } else {
            if (front < back) {
                front = (front - 1) % list.length;
            } else {
                front--;
            }
        }
        list[front] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(size * FACTOR);
        }

        if (isEmpty()) {
            addFirst(item);
            return;
        } else if (back == list.length - 1) {
            back = 0;
        } else {
            back = (back + 1) % list.length;
        }
        list[back] = item;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int temp = front;
        do {
            System.out.print(list[temp] + ", ");
            temp = (temp + 1) % list.length;
        } while (temp != back);
        System.out.print(list[temp]); // Prevent off-by-one bug
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T value = list[front];
        if (front == list.length - 1) {
            list[front] = null;
            front = (front + 1) % list.length;
        } else if (size == 1) {
            list[front] = null;
            back = front = -1;
        } else {
            list[front] = null;
            front++;
        }

        size--;

        if (needShrinking()) {
            resize(list.length / 2);
        }

        return value;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T value = list[back];
        if (size == 1) {
            list[back] = null;
            back = front = -1;

        } else {
            if (back == 0) {
                list[back] = null;
                back = list.length - 1;
            } else {
                list[back] = null;
                back--;
            }
        }

        size--;

        if (needShrinking()) {
            resize(list.length / 2);
        }

        return value;
    }

    @Override
    public T get(int index) {
        if (index >= list.length) {
            return null;
        }
        return list[index];
    }

    @Override
    public T getLast() {
        if (!isEmpty()) {
            return list[back];
        }
        return null;
    }

    public T getFirst() {
        if (!isEmpty()) {
            return list[front];
        }
        return null;
    }

    private void resize(int newSize) {
        T[] newList = (T[]) new Object[newSize];

        /*
         * Resizing is a little tricky here, in shrinking we can't just copy (from 0 to size),
         * there is corner case where (back > front) and therefore the copying with be (from front to back)
         * */
        if (back > front) {
            System.arraycopy(list, front, newList, 0, size);
        } else {
            System.arraycopy(list, 0, newList, 0, size);
        }
        list = newList;
        front = 0;
        back = size - 1;
    }

    private boolean needShrinking() {
        return ((double) size) / list.length < 0.25 && list.length >= 16;
    }

    public int getLength() {
        return list.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int position = 0; // Starting from "front" to "back" will produce off-by-one bug

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public T next() {
                return list[position++];
            }
        };

    }

    @Override
    public String toString() {
        ArrayDeque<String> tempList = new ArrayDeque<>();
        for (T item : this) {
            tempList.addFirst(item.toString());
        }
        return "{" + String.join(", ", tempList) + "}";
    }

    @Override
    public boolean equals(Object o) {

        // Performance wise
        if (o == this) {
            return true;
        }

        if (o instanceof ArrayDeque arrayDequeObj) {
            if (this.size != arrayDequeObj.size) {
                return false;
            }

            for (int i = 0; i < this.getLength(); i++) {
                String value1 = this.get(i) == null ? "Null" : this.get(i).toString();
                String value2 = ((ArrayDeque<?>) o).get(i) == null ? "Null" : ((ArrayDeque<?>) o).get(i).toString();
                if (!value1.equals(value2)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
    * "..." called the variable arguments "varargs",
    * this is same as "arguments" object in JavaScript!
    *
    * Using <Generic> after static keyword converts this method
    * to a generic method, odd right? It's a must to make this method works
    * */
    public static <I> ArrayDeque<I> of(I... items) {
        ArrayDeque<I> newItems = new ArrayDeque<>();
        for(I item: items) {
            newItems.addFirst(item);
        }
        return newItems;
    }
}

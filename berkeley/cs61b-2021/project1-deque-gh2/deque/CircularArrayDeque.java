package deque;

public class CircularArrayDeque<T> implements Deque<T> {
    private T[] list;
    private int front, back;
    private int size;
    private static final int INT_SIZE = 8;
    private final int FACTOR = 2;

    public CircularArrayDeque() {
        list = (T[]) new Object[INT_SIZE];
        front = back = -1;
        size = 0;
    }

    public CircularArrayDeque(int size) {
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
            resize(list.length/2);
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
            resize(list.length/2);
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
        }
        else {
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
}

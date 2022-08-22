package deque;

public interface Deque<T> {
    void addFirst(T var1);

    void addLast(T var1);

    default boolean isEmpty() {
        return this.size() == 0;
    }

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int var1);

    T getLast();
}
package deque;

public class ArrayDeque<T> implements Deque<T> {
    static final int LIST_SIZE = 8;
    private T[] list = (T[]) new Object[8];
    private int size = 0;

    public ArrayDeque() {
    }

    public void addFirst(T item) {
        T[] newList = (T[]) new Object[this.size + 1];
        newList[0] = item;
        if (this.size > 0) {
            System.arraycopy(this.list, 0, newList, 1, this.size);
        }

        this.list = newList;
        ++this.size;
    }

    public void addLast(T item) {
        if (this.size == 0) {
            this.addFirst(item);
        } else {
            if (this.size == this.list.length) {
                int FACTOR = 2;
                T[] newList = (T[]) new Object[this.size * FACTOR];
                System.arraycopy(this.list, 0, newList, 0, this.size);
                this.list = newList;
            }

            this.list[this.size] = item;
            ++this.size;
        }
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        for(int i = 0; i < this.size; ++i) {
            if (i < this.size - 1) {
                Object var10001 = this.list[i];
                System.out.print("" + var10001 + ", ");
            }
        }

        System.out.println(this.list[this.size - 1]);
    }

    public T removeFirst() {
        if (this.size == 0) {
            return null;
        } else {
            T value = this.list[0];
            this.list[0] = null;
            --this.size;
//            int FACTOR = true;
            T[] newList = (T[]) new Object[this.size];
            System.arraycopy(this.list, 1, newList, 0, this.size);
            this.list = newList;
            return value;
        }
    }

    public T removeLast() {
        if (this.size == 1) {
            return this.removeFirst();
        } else if (this.size > 1) {
            T value = this.list[this.size - 1];
            this.list[this.size - 1] = null;
            --this.size;
            return value;
        } else {
            return null;
        }
    }

    public T get(int index) {
        return index >= 0 && index < this.size ? this.list[index] : null;
    }

    public T getLast() {
        return this.list[this.size];
    }

    private void resize(int newSize) {
    }
}

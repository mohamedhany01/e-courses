package deque;

import deque.Deque;

public class CDQList<T> implements Deque<T> {
    private int front;
    private int back;
    private T [] list;
    private int size;

    public CDQList() {
        front = -1;
        back = -1;
        list = (T[]) new Object[8];
        size = 0;
    }

    @Override
    public void addFirst(T value) {
        if ((front == 0 && back == list.length - 1) || front == back - 1) {
            System.out.println("FULL! Can't add in first");
            resize(1);
            return;
        } else if (front == -1) {
            front = back = 0;
        } else if (front == 0) {
            // Add from the end of the list
            front = list.length - 1;
        }
        else {
            front--;
        }
        list[front] = value;
        size++;
    }

    @Override
    public void addLast(T value) {
        if((front == 0 && back == list.length - 1) || front == back - 1) {
            System.out.println("FULL! Can't add in first");
            resize(1);
            return;
        }
        else if (back == -1) {
            addFirst(value);
            return;
        }
        else if (back == list.length - 1) {
            back = 0;
        }
        else {
            back++;
        }
        list[back] = value;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int newFront = front;
        do {
            if (newFront != back) {
                System.out.print(list[newFront] + ",");
            }
            newFront = (newFront + 1) % list.length;
        }while (newFront != back);
        System.out.print(list[newFront]);
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if(front == -1) {
            return null;
        }
        T value = list[front];
        list[front] = null;
        front--;
        size--;
        return value;
    }

    @Override
    public T removeLast() {
        if (back == -1) {
            return null;
        }
        T value = list[back];
        list[back] = null;
        back++;
        size--;
        return value;
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        T value = list[index];
        return value;
    }

    @Override
    public T getLast() {
        if (back == -1) {
            return null;
        }
        return list[back];
    }

    public T getFirst() {
        if (front == -1) {
            return null;
        }
        return list[front];
    }

    private void resize(int newSize) {
        // TODO
    }
}

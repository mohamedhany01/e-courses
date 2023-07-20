package org.sort.heap;

import java.lang.reflect.Array;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {
    public MaxHeap() {
        super();
    }

    public MaxHeap(int heapSize) {
        super(heapSize);
    }

    public MaxHeap(T[] array) {
        super(array);
        this.heaps = heapify(array);
    }


    public T[] heapify(T[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            array = siftDownRecursively(array, i);
            this.size++;
        }
        return array;
    }

    public T[] sort() {
        if (size == 0 || size == 1) {
            return this.heaps;
        }
        return sort(this.heaps, this.size - 1);
    }

    private T[] sort(T[] array, int heapSize) {
        if (heapSize == 0) {
            return array;
        } else {
            swap(array, 0, heapSize);
            heapSize--;
            for (int i = heapSize; i >= 0; i--) {
                array = siftUpRecursively(array, i);
            }
            return sort(array, heapSize);
        }
    }


    public T removeMax() {
        if (isEmpty()) {
            return null;
        }
        if (getSize() == 1) {
            T value = heaps[--size];
            return value;
        }
        T value = this.heaps[ROOT];
        this.heaps[ROOT] = this.heaps[--size];
        this.heaps[size] = null;
        siftDown(ROOT);
        return value;
    }


    private T[] siftUpRecursively(T[] heap, int heapIndex) {
        if (heapIndex != ROOT) {
            int parentIndex = getParent(heapIndex);
            T heapParent = this.heaps[parentIndex];
            T currentHeap = this.heaps[heapIndex];

            if (currentHeap.compareTo(heapParent) > 0) {
                swap(heap, heapIndex, parentIndex);
                siftUpRecursively(heap, parentIndex);
            }
        }
        return heap;
    }

    private void siftUp(int heapIndex) {
        this.heaps = siftUpRecursively(this.heaps, heapIndex);
    }

    public void increaseKey(T key, T newKay) {
        int keyIndex = find(key);

        if (keyIndex != -1) {
            this.heaps[keyIndex] = newKay;
            this.heaps = siftUpRecursively(heaps, keyIndex);
        }
    }

    public boolean add(T heap) {
        if (isReachedMaxLoad()) {
            resize(RESIZE_VALUE);
        }
        this.heaps[size++] = heap;
        siftUp(size - 1);
        return true;
    }

    private void resize(int size) {
        this.initialSize = size * this.initialSize;
        T[] oldHeaps = this.heaps;
        this.heaps = (T[]) Array.newInstance(Comparable.class, this.initialSize);
        this.size = 0;

        for (int i = 0; i < oldHeaps.length; i++) {
            if (oldHeaps[i] == null) {
                break;
            }
            add(oldHeaps[i]);
        }
    }


    private int getMaxIndex(T[] heap, int a, int b) {
        if (heap[a].compareTo(heap[b]) > 0) {
            return a;
        } else if (heap[a].compareTo(heap[b]) == 0) {
            return a;
        } else return b;
    }


    private T[] siftDownRecursively(T[] heap, int rootIndex) {
        if (getRight(rootIndex) >= heap.length || getLeft(rootIndex) >= heap.length) {
            return heap;
        }

        T rightValue = heap[getRight(rootIndex)];
        T leftValue = heap[getLeft(rootIndex)];
        T currentHeap = heap[rootIndex];

        if (rightValue != null && leftValue != null) {
            int maxIndex = getMaxIndex(heap, getRight(rootIndex), getLeft(rootIndex));
            T maxValue = heap[maxIndex];
            if (maxValue.compareTo(currentHeap) > 0) {
                swap(heap, rootIndex, maxIndex);
                return siftDownRecursively(heap, maxIndex);
            }
        }

        if (rightValue == null && leftValue != null) {
            if (leftValue.compareTo(currentHeap) > 0) {
                swap(heap, rootIndex, getLeft(rootIndex));
                return siftDownRecursively(heap, getLeft(rootIndex));
            }
        }

        return heap;
    }

    private void siftDown(int root) {
        this.heaps = siftDownRecursively(this.heaps, root);
    }

}


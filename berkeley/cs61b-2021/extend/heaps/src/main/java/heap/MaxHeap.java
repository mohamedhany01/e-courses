package heap;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {
    public MaxHeap() {
        super();
    }

    public MaxHeap(int heapSize) {
        super(heapSize);
    }


    public T[] heapify(T[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            array = siftDownRecursively(array, i);
        }
        return array;
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
        if (this.size + 1 > this.heaps.length) {
            // TODO resize function
            System.out.println("Resizing...");
            return false;
        }
        this.heaps[size++] = heap;
        siftUp(size - 1);
        return true;
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

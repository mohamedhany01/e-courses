import org.sort.*;
import org.sort.heap.HeapSort;
import org.sort.helper.Helper;

public class PlayGround {
    public static void main(String[] args) {
        final int COUNT = 100;
        Helper helper = new Helper();


        // Quick
        Integer[] array1 = helper.getRandomArrayOfSize(COUNT, 1000);
        QuickSort quickSort = new QuickSort(array1);
        quickSort.sort();
        quickSort.show();

        // Merge
        Integer[] array2 = helper.getRandomArrayOfSize(COUNT, 1000);
        MergeSort mergeSort = new MergeSort(array2);
        mergeSort.sort();
        mergeSort.show();

        // Selection
        Integer[] array3 = helper.getRandomArrayOfSize(COUNT, 1000);
        SelectionSort selectionSort = new SelectionSort(array3);
        selectionSort.sort();
        selectionSort.show();

        // Heap
        Integer[] array4 = helper.getRandomArrayOfSize(COUNT, 1000);
        HeapSort heapSort = new HeapSort(array4);
        heapSort.sort();
        heapSort.show();

        // Insertion
        Integer[] array5 = helper.getRandomArrayOfSize(COUNT, 1000);
        InsertionSort insertionSort = new InsertionSort(array5);
        insertionSort.sort();
        insertionSort.show();

        // Counting
        Integer[] array6 = helper.getRandomArrayOfSize(COUNT, 1000);
        CountingSort countingSort = new CountingSort(array6);
        countingSort.sort();
        countingSort.show();

    }
}

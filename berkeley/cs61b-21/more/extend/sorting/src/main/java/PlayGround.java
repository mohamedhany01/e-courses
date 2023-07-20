import org.sort.InsertionSort;
import org.sort.MergeSort;
import org.sort.QuickSort;
import org.sort.SelectionSort;
import org.sort.heap.HeapSort;
import org.sort.helper.Helper;

public class PlayGround {
    public static void main(String[] args) {
        Helper helper = new Helper();


        // Quick
        Integer[] array1 = helper.getRandomArrayOfSize(20, 1000);
        QuickSort quickSort = new QuickSort(array1);
        quickSort.sort();
        quickSort.show();

        // Merge
        Integer[] array2 = helper.getRandomArrayOfSize(20, 1000);
        MergeSort mergeSort = new MergeSort(array2);
        mergeSort.sort();
        mergeSort.show();

        // Selection
        Integer[] array3 = helper.getRandomArrayOfSize(20, 1000);
        SelectionSort selectionSort = new SelectionSort(array3);
        selectionSort.sort();
        selectionSort.show();

        // Heap
        Integer[] array4 = helper.getRandomArrayOfSize(20, 1000);
        HeapSort heapSort = new HeapSort(array4);
        heapSort.sort();
        heapSort.show();

        // Heap
        Integer[] array5 = helper.getRandomArrayOfSize(20, 1000);
        InsertionSort insertionSort = new InsertionSort(array5);
        insertionSort.sort();
        insertionSort.show();

    }
}

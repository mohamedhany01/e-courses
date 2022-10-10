package deque.extra;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class SinglyLinkedListTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */ public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        SinglyLinkedList<String> lld1 = new SinglyLinkedList<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */ public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */ public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/ public void multipleParamTest() {

        SinglyLinkedList<String> lld1 = new SinglyLinkedList<String>();
        SinglyLinkedList<Double> lld2 = new SinglyLinkedList<Double>();
        SinglyLinkedList<Boolean> lld3 = new SinglyLinkedList<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty SinglyLinkedList. */ public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    @Ignore
    /* Add large number of elements to deque; check if order is correct. */ public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            System.out.println(i);
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    // Extra unit tests
    @Test
    public void getFirstItemFromList() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();

        for (int i = 0; i < 1000; i++) {
            lld1.addFirst(i);
        }

        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
        }

        assertEquals(999, (int) lld1.getFirst());
    }

    @Test
    public void getLastItemFromList() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();

        for (int i = 0; i < 1000; i++) {
            lld1.addFirst(i);
        }

        for (int i = 0; i < 500; i++) {
            lld1.addLast(i);
        }

        assertEquals(499, (int) lld1.getLast());
    }

    @Test
    public void findFooInList() {
        SinglyLinkedList<String> lld1 = new SinglyLinkedList<>();

        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addLast("foo");

        assertEquals("foo", lld1.find("foo").getValue());
    }

    @Test
    public void removeLastFromListHasTwoNodes() {
        SinglyLinkedList<String> lld1 = new SinglyLinkedList<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");

        lld1.removeLast();

        assertEquals("buz", lld1.getFirst());
        assertEquals("buz", lld1.getLast());
        assertEquals(1, lld1.size());
    }

    @Test
    public void removeFirstFromListHasTwoNodes() {
        SinglyLinkedList<String> lld1 = new SinglyLinkedList<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");

        lld1.removeFirst();

        assertEquals("bar", lld1.getFirst());
        assertEquals("bar", lld1.getLast());
        assertEquals(1, lld1.size());
    }

    @Test
    public void removeFromList() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);

        int val = lld1.delete(2);

        assertEquals(1, val);
        assertEquals(3, lld1.size());
    }

    @Test
    public void insertInRandom() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);

        lld1.insertInto(100, 2);

//        assertEquals(7, lld1.size());
    }

    @Test
    public void reverseListIteratively() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        System.out.println("Before reversing:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(5, lld1.getFirst().intValue());
        assertEquals(0, lld1.getLast().intValue());
        assertEquals(6, lld1.size());

        lld1.reverseIteratively();

        System.out.println("\nAfter reversing 1:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(0, lld1.getFirst().intValue());
        assertEquals(5, lld1.getLast().intValue());
        assertEquals(6, lld1.size());

        lld1.reverseIteratively();

        System.out.println("\nAfter reversing 2:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(5, lld1.getFirst().intValue());
        assertEquals(0, lld1.getLast().intValue());
        assertEquals(6, lld1.size());
    }

    @Test
    public void reverseListRecursive() {
        SinglyLinkedList<Integer> lld1 = new SinglyLinkedList<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        System.out.println("Before reversing:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(5, lld1.getFirst().intValue());
        assertEquals(0, lld1.getLast().intValue());
        assertEquals(6, lld1.size());

        lld1.reverseRecursively();

        System.out.println("\nAfter reversing 1:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(0, lld1.getFirst().intValue());
        assertEquals(5, lld1.getLast().intValue());
        assertEquals(6, lld1.size());

        lld1.reverseRecursively();

        System.out.println("\nAfter reversing 2:");
        lld1.printDeque();
        System.out.println("Head:" + lld1.getFirst().intValue() + ", " + " Back:" + lld1.getLast().intValue());

        assertEquals(5, lld1.getFirst().intValue());
        assertEquals(0, lld1.getLast().intValue());
        assertEquals(6, lld1.size());
    }
}

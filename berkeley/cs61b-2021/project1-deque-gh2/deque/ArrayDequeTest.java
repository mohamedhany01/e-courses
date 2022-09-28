package deque;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.junit.Assert.*;


public class ArrayDequeTest {
    final int INPUT = 16;
    // Extra tests
    final int INPUT_SIZE = 100000;

    @Test
    public void testEqualsMethod() {
        deque.ArrayDeque<Integer> list1 = new deque.ArrayDeque<>();
        deque.ArrayDeque<Integer> list2 = new deque.ArrayDeque<>();

        for (int i = 0; i < 5; i++) {
            list1.addFirst(i);
            list2.addFirst(i);
        }

        // Self test
        assertTrue(list1.equals(list1));
        assertTrue(list2.equals(list2));

        // Each other test
        assertTrue(list1.equals(list2));
        assertTrue(list2.equals(list1));

        list1 = new deque.ArrayDeque<>();
        list2 = new deque.ArrayDeque<>();

        for (int i = 0; i < 5; i++) {
            list1.addFirst(i);
        }

        for (int i = 0; i < 10; i++) {
            list2.addFirst(i);
        }

        // Each other test
        assertFalse(list1.equals(list2));
        assertFalse(list2.equals(list1));

        list1 = new deque.ArrayDeque<>();
        list2 = new deque.ArrayDeque<>();

        for (int i = 0; i < 5; i++) {
            list1.addFirst(i);

            if (i == 2) {
                list2.addFirst(10);
            } else {
                list2.addFirst(i);
            }
        }

        // Each other test
        assertFalse(list1.equals(list2));
        assertFalse(list2.equals(list1));

    }

    @Test
    public void displayUsingIterator() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }

//        Iterator<Integer> it = list.iterator();
//        while (it.hasNext()) {
//            System.out.print(it.next() + ", ");
//        }

        System.out.println(list);
    }

    @Test
    public void addToListAndMakeItFullFront() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        assertEquals(INPUT, list.size());
        list.printDeque();
    }

    @Test
    public void addToListAndMakeItFullLast() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }
        assertEquals(INPUT, list.size());
        list.printDeque();
    }

    @Test
    public void removeTwiceAndCheckSize() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        list.removeFirst();
        list.removeFirst();
        assertEquals(INPUT - 2, list.size());
        assertEquals(list.get((list.getLength() / 2) - 1).intValue(), list.getLast().intValue());
        assertEquals(INPUT - 3, list.getFirst().intValue());
    }

    @Test
    public void removeFirst() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        list.removeFirst();
        list.removeFirst();
        assertEquals(INPUT - 2, list.size());
        assertEquals(INPUT - 3, list.getFirst().intValue());
    }

    @Test
    public void removeLast() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }
        list.removeLast();
        list.removeLast();
        assertEquals(INPUT - 2, list.size());
        assertEquals(INPUT - 3, list.getLast().intValue());
    }

    @Test
    public void removeFromLastToFirst() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < INPUT; i++) {
            list.removeLast();
        }
        assertEquals(0, list.size());
        assertNull(list.getLast());
        assertNull(list.getFirst());
    }

    @Test
    public void removeFromFirstToLast() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        for (int i = 0; i < INPUT; i++) {
            list.removeFirst();
        }
        assertEquals(0, list.size());
        assertNull(list.getLast());
        assertNull(list.getFirst());
    }

    @Test
    public void emptyListAndGetLastAndFirstItem() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            list.addFirst(i);
        }
        for (int i = 4; i < INPUT; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < 4; i++) {
            list.removeLast();
        }
        for (int i = 0; i < 4; i++) {
            list.removeFirst();
        }
        assertNull(list.get(0));
        assertNull(list.get(INPUT - 1));
    }

    @Test
    public void getSomething() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }

        assertEquals(0, list.getFirst().intValue());
        assertEquals(INPUT - 1, list.getLast().intValue());
        assertEquals(0, list.get(0).intValue());
        assertEquals(INPUT - 1, list.get(INPUT - 1).intValue());

        assertNull(list.get(INPUT * 2));
    }

    @Test
    public void JavaDSVSList() {
        Deque<String> l = new ArrayDeque<>();
        deque.ArrayDeque<String> list = new deque.ArrayDeque<>();

        l.addFirst("banana");
        l.addFirst("cherry");
        l.addFirst("grape");
        l.addFirst("lemon");
        l.addFirst("coconut");
        l.addFirst("kiwi");

        list.addFirst("banana");
        list.addFirst("cherry");
        list.addFirst("grape");
        list.addFirst("lemon");
        list.addFirst("coconut");
        list.addFirst("kiwi");

        assertEquals(l.getFirst(), list.getFirst());
        assertEquals(l.getLast(), list.getLast());

        Iterator<String> it = l.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        list.printDeque();

        l.removeFirst();
        l.removeFirst();
        list.removeFirst();
        list.removeFirst();

        assertEquals(l.removeFirst(), list.removeFirst());
        l.addLast("foo");
        list.addLast("foo");

        assertEquals(l.getLast(), list.getLast());
        assertEquals(l.removeLast(), list.removeLast());
    }

    // Edge cases testing
    @Test
    public void testEdgeCasesFromEndAndFront() {
        /*
         * Source of the test: https://www.youtube.com/watch?v=WJres9mgiAk
         * */
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        list.addLast(2);
        list.addLast(-1);
        list.addLast(0);
        assertEquals(2, list.removeFirst().intValue());
        assertEquals(-1, list.getFirst().intValue());
        assertEquals(0, list.getLast().intValue());

        list.addFirst(4);
        assertEquals(4, list.getFirst().intValue());
        assertEquals(0, list.getLast().intValue());

        list.addFirst(7);
        assertEquals(7, list.getFirst().intValue());
        assertEquals(7, list.removeFirst().intValue());
        assertEquals(4, list.getFirst().intValue());

        assertEquals(0, list.removeLast().intValue());
        assertEquals(-1, list.getLast().intValue());

        list.addFirst(7);
        assertEquals(7, list.getFirst().intValue());
        assertEquals(-1, list.removeLast().intValue());
        assertEquals(4, list.getLast().intValue());
        assertEquals(4, list.removeLast().intValue());
        assertEquals(7, list.getLast().intValue());
        assertEquals(7, list.removeLast().intValue());
        assertNull(list.getLast());
        assertNull(list.getFirst());
        assertEquals(0, list.size());
    }

    @Test
    public void trickyDisplay() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>(5);
        list.addFirst(2);
        list.addFirst(5);
        list.addLast(-1);
        list.addLast(0);
        list.addFirst(7);
        list.addFirst(4); // Should resize here
        assertEquals(6, list.size());
        list.printDeque();

    }

    @Test
    public void arrayDequeDemo() {
        /*
         * Source of the test
         * https://docs.google.com/presentation/d/1XBJOht0xWz1tEvLuvOL4lOIaY0NSfArXAvqgkrx0zpc/edit#slide=id.g1094ff4355_0_35
         * */
        deque.ArrayDeque<Character> list = new deque.ArrayDeque<>();
        list.addLast('a');
        list.addLast('b');
        list.addFirst('c');
        list.addLast('d');
        list.addLast('e');
        list.addFirst('f');
        assertEquals(6, list.size());
        list.printDeque();
    }

    @Test
    @Ignore
    // Only Enable it when you need to check the performance
    public void largeList() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>(100000000);
        for (int i = 0; i < 100000000; i++) {
            list.addFirst(i);
        }
        assertEquals(100000000, list.size());
    }

    @Test
    public void performanceTestInShrinkingLast() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            list.addFirst(i);
        }
        for (int i = 0; i < 9999; i++) {
            list.removeLast();
        }

        assertEquals(1, list.size());
        assertEquals(8, list.getLength());

    }

    @Test
    public void performanceTestInShrinkingFront() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < 9999; i++) {
            list.removeFirst();
        }

        assertEquals(1, list.size());
        assertEquals(8, list.getLength());

    }

    @Test
    @Ignore
    // Not the same logic of mine
    public void testTricky() {
        // Source: https://gitlab.cs.washington.edu/cse332-20wi-students/skeleton/-/blob/master/deque/ArrayDequeTest.java
        deque.ArrayDeque<Integer> deque = new deque.ArrayDeque<>();
        deque.addFirst(0);
        assertEquals(0, (int) deque.get(0));

        deque.addLast(1);
        assertEquals(1, (int) deque.get(1));

        deque.addFirst(-1);
        deque.addLast(2);
        assertEquals(2, (int) deque.get(3));

        deque.addLast(3);
        deque.addLast(4);

        // Test that removing and adding back is okay
        assertEquals(-1, (int) deque.removeFirst());
        deque.addFirst(-1);
        assertEquals(-1, (int) deque.get(0));

        deque.addLast(5);
        deque.addFirst(-2);
        deque.addFirst(-3);

        // Test a tricky sequence of removes
        assertEquals(-3, (int) deque.removeFirst());
        assertEquals(5, (int) deque.removeLast());
        assertEquals(4, (int) deque.removeLast());
        assertEquals(3, (int) deque.removeLast());
        assertEquals(2, (int) deque.removeLast());
        // Failing test
        int actual = deque.removeLast();
        assertEquals(1, actual);
    }

    // Extra tests
    @Test
    public void removeLastFromListHasTwoNodes() {
        deque.ArrayDeque<String> lld1 = new deque.ArrayDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");

        assertEquals(5, lld1.size());
    }

    @Test
    public void listIsEmptyNoItemInside() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        assertNull(list.getFirst());
        assertNull(list.getLast());
        assertEquals(0, list.size());
    }

    @Test
    public void oneItemAddedInFrontList() {

        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        list.addFirst(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void oneItemAddedInEndList() {

        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        list.addLast(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void addItemsInEndList() {
        final int VALUES_SIZE = 50000;
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        for (int i = 0; i < VALUES_SIZE; i++) {

            list.addLast(i);
        }
        assertEquals(0, list.getFirst().intValue());
        assertEquals(VALUES_SIZE - 1, list.getLast().intValue());
        assertEquals(VALUES_SIZE, list.size());
    }

    @Test
    public void removeOneItem() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        list.addFirst(5);
        assertEquals(5, list.removeFirst().intValue());
        assertEquals(0, list.size());
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }

    @Test
    public void removeTwoFromFirstWithManyInList() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();

        list.addFirst(5);
        list.addFirst(10);
        list.addFirst(8);
        assertEquals(8, list.removeFirst().intValue());

        assertEquals(2, list.size());
        assertEquals(10, list.getFirst().intValue());
        assertEquals(5, list.getLast().intValue());
        assertEquals(10, list.removeFirst().intValue());
        assertEquals(1, list.size());

        assertEquals(5, list.getFirst().intValue());
        assertEquals(5, list.getLast().intValue());
    }

    @Test
    public void removeTwoFromEndWithManyInList() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();

        list.addFirst(5);
        list.addFirst(10);
        list.addFirst(8);

        assertEquals(5, list.removeLast().intValue());
        assertEquals(2, list.size());
        assertEquals(8, list.getFirst().intValue());
        assertEquals(10, list.getLast().intValue());

        assertEquals(10, list.removeLast().intValue());
        assertEquals(1, list.size());
        assertEquals(8, list.getFirst().intValue());
        assertEquals(8, list.getLast().intValue());
    }

    @Test
    public void simpleTest() {
        deque.ArrayDeque<Integer> list = new deque.ArrayDeque<>();
        list.addFirst(2);
        list.addFirst(5);
        list.addLast(-1);
        list.addLast(0);
        list.addFirst(7);
        list.addFirst(4);
        list.addFirst(10);
        list.addFirst(12);
        assertEquals(12, list.getFirst().intValue());
        assertEquals(0, list.getLast().intValue());
        assertEquals(8, list.size());
        list.printDeque();
    }

    // Tests took from lab 3
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */ public void addIsEmptySizeTest() {

        deque.ArrayDeque<String> lld1 = new deque.ArrayDeque<String>();

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

        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<Integer>();
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

        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
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
    /* Check if you can create deque.ArrayDeques with different parameterized types*/ public void multipleParamTest() {

        deque.ArrayDeque<String> lld1 = new deque.ArrayDeque<String>();
        deque.ArrayDeque<Double> lld2 = new deque.ArrayDeque<Double>();
        deque.ArrayDeque<Boolean> lld3 = new deque.ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty deque.ArrayDeque. */ public void emptyNullReturnTest() {

        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    @Ignore
    // Only Enable it when you need to check the performance
    /* Add large number of elements to deque; check if order is correct. */ public void bigLLDequeTest() {

        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(i + " Added");
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            System.out.println(i + " Removed");
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            System.out.println(i);
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void emptyList() {

        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        assertEquals(0, lld1.size());
    }

    @Test
    public void addInputFromFront() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addFirst(i);
        }
        assertEquals(INPUT_SIZE, lld1.size());
    }

    @Test
    public void addInputsFromEnd() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addLast(i);
        }
        assertEquals(INPUT_SIZE, lld1.size());
    }

    @Test
    public void addInputsFromFrontAndEnd() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addLast(i);
        }
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addLast(i);
        }
        assertEquals(INPUT_SIZE + INPUT_SIZE, lld1.size());
    }

    @Test
    public void addAndRemoveOneItemFromEndAndMakeListEmpty() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        lld1.addLast(3);
        lld1.addLast(50);
        lld1.addLast(70);
        lld1.removeLast();
        int result = lld1.removeFirst();
        assertEquals(1, lld1.size());
        assertEquals(3, result);
    }

    @Test
    public void addAndRemoveOneItemFromFrontAndMakeListEmpty() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        lld1.addFirst(3);
        int result = lld1.removeFirst();
        assertEquals(0, lld1.size());
        assertEquals(3, result);
    }

    @Test
    public void removeFromFrontAndEnd() {
        deque.ArrayDeque<Integer> lld1 = new deque.ArrayDeque<>();
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addLast(i);
        }
        for (int i = 0; i < INPUT_SIZE; i++) {
            lld1.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            lld1.removeFirst();
        }
        for (int i = 0; i < 20; i++) {
            lld1.removeLast();
        }
        assertEquals(INPUT_SIZE + INPUT_SIZE - 30, lld1.size());
    }
}

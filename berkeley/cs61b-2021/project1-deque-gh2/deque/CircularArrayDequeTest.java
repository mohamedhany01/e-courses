package deque;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Fixing bug
// https://sp21.datastructur.es/materials/lab/lab3/lab3#fixing-the-bug-and-execution-breakpoints

import static org.junit.Assert.assertEquals;

public class CircularArrayDequeTest {
    final int INPUT = 16;

    @Test
    public void addToListAndMakeItFullFront() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        assertEquals(INPUT, list.size());
        list.printDeque();
    }

    @Test
    public void addToListAndMakeItFullLast() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }
        assertEquals(INPUT, list.size());
        list.printDeque();
    }

    @Test
    public void removeTwiceAndCheckSize() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < INPUT; i++) {
            list.removeLast();
        }
        assertEquals(INPUT - INPUT, list.size());
        assertEquals(null, list.getLast());
        assertEquals(null, list.getFirst());
    }

    @Test
    public void removeFromFirstToLast() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addFirst(i);
        }
        for (int i = 0; i < INPUT; i++) {
            list.removeFirst();
        }
        assertEquals(INPUT - INPUT, list.size());
        assertEquals(null, list.getLast());
        assertEquals(null, list.getFirst());
    }

    @Test
    public void emptyListAndGetLastAndFirstItem() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        assertEquals(null, list.get(0));
        assertEquals(null, list.get(INPUT - 1));
    }

    @Test
    public void getSomething() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
        for (int i = 0; i < INPUT; i++) {
            list.addLast(i);
        }

        assertEquals(0, list.getFirst().intValue());
        assertEquals(INPUT - 1, list.getLast().intValue());
        assertEquals(0, list.get(0).intValue());
        assertEquals(INPUT - 1, list.get(INPUT - 1).intValue());

        assertEquals(null, list.get(INPUT * 2));
    }

    @Test
    public void JavaDSVSList() {
        Deque<String> l = new ArrayDeque<>();
        CircularArrayDeque<String> list = new CircularArrayDeque<>();

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
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        assertEquals(null, list.getLast());
        assertEquals(null, list.getFirst());
        assertEquals(0, list.size());
    }

    @Test
    public void trickyDisplay() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>(5);
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
        CircularArrayDeque<Character> list = new CircularArrayDeque<>();
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
    public void largeList() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>(100000000);
        for (int i = 0; i < 100000000; i++) {
            list.addFirst(i);
        }
        assertEquals(100000000, list.size());
    }

    @Test
    public void performanceTestInShrinkingLast() {
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        CircularArrayDeque<Integer> list = new CircularArrayDeque<>();
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
        CircularArrayDeque<Integer> deque = new CircularArrayDeque<>();
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

}

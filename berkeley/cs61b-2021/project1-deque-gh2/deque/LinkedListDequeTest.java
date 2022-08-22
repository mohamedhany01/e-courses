package deque;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkedListDequeTest {

    @Test
    public void removeLastFromListHasTwoNodes() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");

        assertEquals(5, lld1.size());
    }

    @Test
    public void listIsEmptyNoItemInside() {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        assertEquals(null, list.getFirst());
        assertEquals(null, list.getLast());
        assertEquals(0, list.size());
    }

    @Test
    public void oneItemAddedInFrontList() {

        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        list.addFirst(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void oneItemAddedInEndList() {

        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        list.addLast(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void addItemsInFrontList() {
        final int VALUES_SIZE = 50000;
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        for (int i = 0; i < VALUES_SIZE; i++) {

            list.addFirst(i);
        }
        assertEquals(VALUES_SIZE - 1, list.getFirst().intValue());
        assertEquals(0, list.getLast().intValue());
        assertEquals(VALUES_SIZE, list.size());
    }

    @Test
    public void addItemsInEndList() {
        final int VALUES_SIZE = 50000;
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        for (int i = 0; i < VALUES_SIZE; i++) {

            list.addLast(i);
        }
        assertEquals(0, list.getFirst().intValue());
        assertEquals(VALUES_SIZE - 1, list.getLast().intValue());
        assertEquals(VALUES_SIZE, list.size());
    }

    @Test
    public void getItem() {
        final int VALUES_SIZE = 50000;
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        for (int i = 0; i < VALUES_SIZE; i++) {

            list.addLast(i);
        }
        assertEquals(5, list.get(5).intValue());
        assertEquals(6, list.get(6).intValue());
        assertEquals(100, list.get(100).intValue());
        assertEquals(0, list.get(0).intValue());
        assertEquals(VALUES_SIZE - 1, list.get(VALUES_SIZE - 1).intValue());
        assertEquals(0, list.get(VALUES_SIZE).intValue());
        assertEquals(null, list.get(-1));
    }

    @Test
    public void removeOneItem() {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        list.addFirst(5);
        assertEquals(5, list.removeFirst().intValue());
        assertEquals(0, list.size());
        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
    }

    @Test
    public void removeTwoFromFirstWithManyInList() {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();

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
        LinkedListDeque<Integer> list = new LinkedListDeque<>();

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
}
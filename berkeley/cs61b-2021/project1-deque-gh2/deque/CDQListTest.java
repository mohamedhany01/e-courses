package deque;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CDQListTest {

    @Test
    public void removeLastFromListHasTwoNodes() {
        CDQList<String> lld1 = new CDQList<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");

        assertEquals(5, lld1.size());
    }

    @Test
    public void listIsEmptyNoItemInside() {
        CDQList<Integer> list = new CDQList<>();
        assertEquals(null, list.getFirst());
        assertEquals(null, list.getLast());
        assertEquals(0, list.size());
    }

    @Test
    public void oneItemAddedInFrontList() {

        CDQList<Integer> list = new CDQList<>();
        list.addFirst(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void oneItemAddedInEndList() {

        CDQList<Integer> list = new CDQList<>();
        list.addLast(1);

        assertEquals(1, list.getFirst().intValue());
        assertEquals(1, list.getLast().intValue());
        assertEquals(1, list.size());
    }

    @Test
    public void addItemsInFrontList() {
        final int VALUES_SIZE = 50000;
        CDQList<Integer> list = new CDQList<>();
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
        CDQList<Integer> list = new CDQList<>();
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
        CDQList<Integer> list = new CDQList<>();
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
        CDQList<Integer> list = new CDQList<>();
        list.addFirst(5);
        assertEquals(5, list.removeFirst().intValue());
        assertEquals(0, list.size());
        assertEquals(null, list.getFirst());
        assertEquals(null, list.getLast());
    }

    @Test
    @Ignore
    public void removeTwoFromFirstWithManyInList() {
        CDQList<Integer> list = new CDQList<>();

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
        CDQList<Integer> list = new CDQList<>();

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
        CDQList<Integer> list = new CDQList<>();
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
}
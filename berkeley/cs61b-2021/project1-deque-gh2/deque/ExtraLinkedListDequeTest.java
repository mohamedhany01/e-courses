package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

public class ExtraLinkedListDequeTest {
    public ExtraLinkedListDequeTest() {
    }

    @Test
    public void getFirstItemFromList() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();

        int i;
        for(i = 0; i < 1000; ++i) {
            lld1.addFirst(i);
        }

        for(i = 0; i < 1000; ++i) {
            lld1.addLast(i);
        }

        Assert.assertEquals(999L, (long)(Integer)lld1.getFirst());
    }

    @Test
    public void getLastItemFromList() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();

        int i;
        for(i = 0; i < 1000; ++i) {
            lld1.addFirst(i);
        }

        for(i = 0; i < 500; ++i) {
            lld1.addLast(i);
        }

        Assert.assertEquals(499L, (long)(Integer)lld1.getLast());
    }

    @Test
    public void findFooInList() {
        LinkedListDeque<String> lld1 = new LinkedListDeque();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addLast("foo");
        Assert.assertEquals("foo", lld1.find("foo").getValue());
    }

    @Test
    public void removeLastFromListHasTwoNodes() {
        LinkedListDeque<String> lld1 = new LinkedListDeque();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.removeLast();
        Assert.assertEquals("buz", lld1.getFirst());
        Assert.assertEquals("buz", lld1.getLast());
        Assert.assertEquals(1L, (long)lld1.size());
    }

    @Test
    public void removeFirstFromListHasTwoNodes() {
        LinkedListDeque<String> lld1 = new LinkedListDeque();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.removeFirst();
        Assert.assertEquals("bar", lld1.getFirst());
        Assert.assertEquals("bar", lld1.getLast());
        Assert.assertEquals(1L, (long)lld1.size());
    }

    @Test
    public void insertInRandom() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.insertInto(100, 2);
    }

    @Test
    public void listIsEmptyNoItemInside() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        Assert.assertEquals((Object)null, list.getFirst());
        Assert.assertEquals((Object)null, list.getLast());
        Assert.assertEquals(0L, (long)list.size());
    }

    @Test
    public void oneItemAddedInFrontList() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        list.addFirst(1);
        Assert.assertEquals(1L, (long)(Integer)list.getFirst());
        Assert.assertEquals(1L, (long)(Integer)list.getLast());
        Assert.assertEquals(1L, (long)list.size());
    }

    @Test
    public void oneItemAddedInEndList() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        list.addLast(1);
        Assert.assertEquals(1L, (long)(Integer)list.getFirst());
        Assert.assertEquals(1L, (long)(Integer)list.getLast());
        Assert.assertEquals(1L, (long)list.size());
    }

    @Test
    public void addItemsInFrontList() {
        int VALUES_SIZE = '썐';
        LinkedListDeque<Integer> list = new LinkedListDeque();

        for(int i = 0; i < 50000; ++i) {
            list.addFirst(i);
        }

        Assert.assertEquals(49999L, (long)(Integer)list.getFirst());
        Assert.assertEquals(0L, (long)(Integer)list.getLast());
        Assert.assertEquals(50000L, (long)list.size());
    }

    @Test
    public void addItemsInEndList() {
        int VALUES_SIZE = '썐';
        LinkedListDeque<Integer> list = new LinkedListDeque();

        for(int i = 0; i < 50000; ++i) {
            list.addLast(i);
        }

        Assert.assertEquals(0L, (long)(Integer)list.getFirst());
        Assert.assertEquals(49999L, (long)(Integer)list.getLast());
        Assert.assertEquals(50000L, (long)list.size());
    }

    @Test
    public void getItem() {
        int VALUES_SIZE = '썐';
        LinkedListDeque<Integer> list = new LinkedListDeque();

        for(int i = 0; i < 50000; ++i) {
            list.addLast(i);
        }

        Assert.assertEquals(5L, (long)(Integer)list.get(5));
        Assert.assertEquals(6L, (long)(Integer)list.get(6));
        Assert.assertEquals(100L, (long)(Integer)list.get(100));
        Assert.assertEquals(0L, (long)(Integer)list.get(0));
        Assert.assertEquals(49999L, (long)(Integer)list.get(49999));
        Assert.assertEquals(0L, (long)(Integer)list.get(50000));
        Assert.assertEquals((Object)null, list.get(-1));
    }

    @Test
    public void removeOneItem() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        list.addFirst(5);
        Assert.assertEquals(5L, (long)(Integer)list.removeFirst());
        Assert.assertEquals(0L, (long)list.size());
        Assert.assertEquals((Object)null, list.getHead());
        Assert.assertEquals((Object)null, list.getTail());
    }

    @Test
    public void removeTwoFromFirstWithManyInList() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        list.addFirst(5);
        list.addFirst(10);
        list.addFirst(8);
        Assert.assertEquals(8L, (long)(Integer)list.removeFirst());
        Assert.assertEquals(2L, (long)list.size());
        Assert.assertEquals(10L, (long)(Integer)list.getFirst());
        Assert.assertEquals(5L, (long)(Integer)list.getLast());
        Assert.assertEquals(10L, (long)(Integer)list.removeFirst());
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertEquals(5L, (long)(Integer)list.getFirst());
        Assert.assertEquals(5L, (long)(Integer)list.getLast());
    }

    @Test
    public void removeTwoFromEndWithManyInList() {
        LinkedListDeque<Integer> list = new LinkedListDeque();
        list.addFirst(5);
        list.addFirst(10);
        list.addFirst(8);
        Assert.assertEquals(5L, (long)(Integer)list.removeLast());
        Assert.assertEquals(2L, (long)list.size());
        Assert.assertEquals(8L, (long)(Integer)list.getFirst());
        Assert.assertEquals(10L, (long)(Integer)list.getLast());
        Assert.assertEquals(10L, (long)(Integer)list.removeLast());
        Assert.assertEquals(1L, (long)list.size());
        Assert.assertEquals(8L, (long)(Integer)list.getFirst());
        Assert.assertEquals(8L, (long)(Integer)list.getLast());
    }

    @Test
    public void randomizedTest() {
        Deque<Integer> L = new LinkedListDeque();
        Deque<Integer> B = new LinkedListDeque();
        int N = 5000;

        for(int i = 0; i < N; ++i) {
            int operationNumber = StdRandom.uniform(0, 3);
            int vL;
            if (operationNumber == 0) {
                vL = StdRandom.uniform(0, 100);
                L.addLast(vL);
                B.addLast(vL);
                System.out.println("addLast(" + vL + ")");
            } else if (operationNumber == 1) {
                vL = L.size();
                System.out.println("size: " + vL);
            } else if (L.size() > 0) {
                vL = (Integer)L.getLast();
                int vB = (Integer)B.getLast();
                if (vL == vB) {
                    System.out.println("They are equal");
                } else {
                    System.out.println("They aren't equal");
                }

                L.removeLast();
                B.removeLast();
            }
        }

    }
}
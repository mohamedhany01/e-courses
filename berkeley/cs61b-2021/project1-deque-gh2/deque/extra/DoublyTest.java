package deque;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoublyTest {

    @Test
    public void removeLastFromListHasTwoNodes() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");
        lld1.printDeque();

        assertEquals(5, lld1.size());
    }
      @Test
    public void printNode() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");
        lld1.printDeque();

    }
    @Test
    public void reversNode() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");
        lld1.printListReversed();

    }

    @Test
    public void addToLast() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("bar");
        lld1.addFirst("buz");
        lld1.addFirst("zuo");
        lld1.addFirst("abo");
        lld1.addFirst("bee");
        lld1.addLast("qqq");
        lld1.addLast("aaa");
        lld1.printDeque();
    }
}

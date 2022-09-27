package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private Node head;
    private Node tail;
    private int size;

    public LinkedListDeque() {
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(position++);
            }
        };
    }

    public int size() {
        return size;
    }

    /*
     * Edge Cases
     * - If you're adding a node for the first time then head.previous will be "null",
     * we can't access "previous" so must check first before accessing.
     * - If tail is "null", just make it point to head which is "null", as well
     * */
    public void addFirst(T value) {
        Node node = new Node(value);

        node.next = head;
        if (head != null) {
            head.previous = node;
        }
        head = node;
        if (head.next == null) {
            head.next = node;
        }
        if (tail != null) {
            tail.next = node;
        }
        if (tail == null) {
            tail = head;
        }
        head.previous = tail;
        size++;
    }

    /*
     * Edge Cases
     * - If tail is "null", make it do the same code in addFirst()
     * - If tail isn't "null", just make its "next" point to the new node,
     * and "previous" to the tail then make tail points to the new node
     * */
    public void addLast(T value) {
        if (tail == null) {
            addFirst(value);
            return;
        }
        Node node = new Node(value);
        tail.next = node;
        node.previous = tail;
        tail = node;
        tail.next = head;
        size++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void printDeque() {
        Node temp = head;
        if (head != null) {
            do {
                System.out.print(temp.value + ",");
                temp = temp.next;
            } while (temp != head);

            System.out.println();
        }
    }

    public void printListReversed() {

    }

    /*
     * Edge Cases
     * - If head is "null", then tail must be "null", as well
     * - If head is not "null", just make the current head points to head.next
     * */
    public T removeFirst() {
        if (head == null) {
            tail = null;
            return null;
        }

        T value = head.value;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return value;
        }
        Node temp = head;
        head = head.next;
        head.previous = tail;
        tail.next = head;
        temp.next = null;
        temp.previous = null;
        temp = null;
        size--;

        return value;
    }

    /*
     * Edge Cases
     * - If there is one node or "null" then "removeFirst()":
     *      - If there is a node the head will point to "null"
     *      - If there isn't a node this both head and tail point to "null"
     * - If list contains more than one node, just loop starting from the head until
     * the node just before the tail. Make the current node is the tail make the next
     * node "previous tail node", make it "null".
     * */
    public T removeLast() {
        if (size <= 1) {
            return removeFirst();
        }
        T value = tail.value;
        tail = tail.previous;
        tail.next = head;
        head.previous = tail;
        size--;

        return value;
    }

    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    // Extra methods
    public T getFirst() {
        if (head == null) {
            return null;
        }
        return head.value;
    }

    public T getLast() {
        if (tail == null) {
            return null;
        }
        return tail.value;
    }

    public Node find(T value) {
        Node temp = head;
        while (temp != null) {
            if (value.equals(temp.value)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    /*
     * Edge Cases
     * - If index is "0" means that we just have one node, then we can call "removeFirst()"
     * - If index is "size - 1" means index is for last node, then we can call "removeLast()"
     * - If not the end or start node, then loop until you just be before the target node
     * then make the current node points to node after node next to it
     * */
    public T delete(int index) {
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        Node previousNode = (Node) get(index - 1);
        Node temp = previousNode;
        T value = previousNode.getNext().value;
        previousNode.next = previousNode.next.next;
        previousNode.next.previous = previousNode;
        temp.next = null;
        temp.previous = null;
        size--;
        return value;
    }

    /*
     * Edge Cases
     * - If index is "0" means that we just have one node, then we can call "addFirst()"
     * - If index is "size - 1" means we in the end of the list, then we can call "addLast()"
     * - If not the end or start node, then loop until you just be before the target node
     * then insert the new node
     * */
    public void insertInto(T value, int index) {
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        Node temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node node = new Node(value);
        node.next = temp.next;
        if (temp.next != null) {
            temp.next.previous = node;
        }
        temp.next = node;
        node.previous = temp;
        size++;
    }

    /*
     * Edge Cases
     * - If index is "0" means that we just have one node, then we can call "addFirst()"
     * - If index is "size - 1" means we in the end of the list, then we can call "addLast()"
     * - If not the end or start node, then loop until you just be the target node
     * then insert the new node after it
     * */
    public void insertAfter(T value, int index) {
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        Node node = new Node(value);
        node.next = temp.next;
        if (temp.next != null) {
            temp.next.previous = node;
        }
        temp.next = node;
        node.previous = temp;
        size++;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public class Node {
        private final T value;
        private Node next;
        private Node previous;
        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return previous;
        }
    }
}
package deque.extra;

import deque.Deque;

public class SinglyLinkedList<T> implements Deque<T> {
    private Node head;
    private Node tail;
    private int size;
    public SinglyLinkedList() {
        size = 0;
    }
    public class Node {
        public Node(T value) {
            this.value = value;
        }

        private T value;
        private Node next;

        public T getValue() {
            return value;
        }
        public Node getNode() {return next; }
    }
    public int size() {
        return size;
    }
    /*
     * Edge Cases
     * - If head is "null", the new node will make it its "next" which is "null".
     * - If tail is "null", just make it point to head which is "null", as well.
     * */
    public void addFirst(T value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = head;
        }
        size++;
    }
    /*
     * Edge Cases
     * - If tail is "null", make it do the same code in addFirst()
     * - If tail isn't "null", just make its "next" point to the new node, then make tail points to the new node
     * */
    public void addLast(T value) {
        if (tail == null) {
            addFirst(value);
            return;
        }
        Node node = new Node(value);
        tail.next = node;
        tail = node;
        size++;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public void printDeque() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.value + ",");
            temp = temp.next;
        }
        System.out.println();
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

        head = head.next;
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
        Node nodeBeforeLastOne = (Node) get(size - 2);
        tail = nodeBeforeLastOne;
        tail.next = null;
        size--;

        return value;
    }
    public T get(int index) {
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return (T) temp;
    }

    // Extra methods
    public T getFirst() {
        return head.value;
    }
    public T getLast() {
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
        Node prevNode = (Node) get(index - 1);
        T value = prevNode.getNode().value;
        prevNode.next = prevNode.next.next;
        size--;
        return  value;
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
        temp.next = node;
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
        temp.next = node;
        size++;
    }
}

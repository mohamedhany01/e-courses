// Node class is implemented for you, no need to look for bugs here!
class DoublyLinkedNode {
    constructor(val) {
        this.value = val;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    constructor() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    // Time O(1)
    // Space O(1)
    addToHead(val) {
        if (!this.head) {
            this.head = new DoublyLinkedNode(val);
            this.tail = this.head;
            this.length++;
            return
        }

        const newNode = new DoublyLinkedNode(val);
        this.head.prev = newNode;
        newNode.next = this.head;
        this.head = newNode;
        this.length++;
    }

    // Time O(1)
    // Space O(1)
    addToTail(val) {
        if (!this.head) {
            this.addToHead(val);
            return;
        }

        const newNode = new DoublyLinkedNode(val);

        newNode.prev = this.tail;
        this.tail.next = newNode;
        this.tail = newNode;

        this.length++;
    }

    // Time O(1)
    // Space O(1)
    removeFromHead() {
        if (!this.head) {
            return undefined;
        }

        if (this.length === 1) {
            return this.removeFromTail();
        }

        const currentValue = this.head.value;

        this.head = this.head.next;
        this.head.prev = null;

        this.length--;

        return currentValue;
    }

    // Time O(1)
    // Space O(1)
    removeFromTail() {
        if (!this.head) {
            return undefined;
        }

        if (this.length === 1) {
            const currentValue = this.head.value;

            this.head = null;
            this.tail = this.head;

            this.length--;

            return currentValue;
        }

        const currentValue = this.tail.value;

        this.tail = this.tail.prev;
        this.tail.next = null;

        this.length--;

        return currentValue;
    }

    // Time O(1)
    // Space O(1)
    peekAtHead() {
        if (!this.head) {
            return undefined;
        }

        return this.head.value;
    }

    // Time O(1)
    // Space O(1)
    peekAtTail() {
        if (!this.head) {
            return undefined;
        }

        return this.tail.value;
    }
}

module.exports = {
    DoublyLinkedList,
    DoublyLinkedNode
}

const { SinglyLinkedNode } = require("./01-singly-linked-list");

class Queue {

    constructor() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    // Time O(1)
    // Space O(1)
    addToHead(val) {
        if (!this.head) {
            this.head = new SinglyLinkedNode(val);
            this.tail = this.head;

        } else {
            const newNode = new SinglyLinkedNode(val);
            newNode.next = this.head;
            this.head = newNode;
        }

        this.length++;

        return this.length;
    }

    // Time O(1)
    // Space O(1)
    addToTail(val) {
        if (!this.head) {
            this.addToHead(val);
            this.tail = this.head;
        } else {
            const newNode = new SinglyLinkedNode(val);
            this.tail.next = newNode;
            this.tail = newNode;
            this.length++;
        }

        return this.length;
    }

    // Time O(1)
    // Space O(1)
    removeFromHead() {
        if (!this.head) {
            return null;
        }

        const currentNode = this.head.value;

        this.head = this.head.next;


        if(this.length === 1) {
            this.tail = this.head;
        }

        this.length--;

        return currentNode;
    }

    // Time O(1)
    // Space O(1)
    enqueue(val) {
        return this.addToTail(val);
    }

    // Time O(1)
    // Space O(1)
    dequeue() {
        return this.removeFromHead();
    }

}

module.exports = {
    Queue,
    SinglyLinkedNode
}

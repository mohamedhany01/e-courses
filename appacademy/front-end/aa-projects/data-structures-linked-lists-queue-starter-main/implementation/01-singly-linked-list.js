// Node class is implemented for you, no need to look for bugs here!
class SinglyLinkedNode {
    constructor(val) {
        this.value = val;
        this.next = null;
    }
}

class SinglyLinkedList {
    constructor() {
        this.head = null;
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

        return {
            head: {
                value: this.head.value,
                next: this.head.next
            },
            length: this.length
        };
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

        return {
            head: {
                value: this.head.value,
                next: this.head.next,
            },
            length: this.length
        };
    }

    // Time O(1)
    // Space O(1)
    removeFromHead() {
        if (!this.head) {
            return undefined;
        }

        const currentNode = this.head.value;

        this.head = this.head.next;

        this.length--;

        // return this.head?.value
        return {
            value: currentNode,
            next: this.head
        }
    }

    // Time O(N)
    // Space O(1)
    removeFromTail() {
        if (!this.head) {
            return undefined;
        }

        if (this.length === 1) {
            this.head = null;
            this.tail = this.head;

            this.length--;

            return this.head;
        }

        let currentNode = this.head;

        while (currentNode.next.next) {
            currentNode = currentNode.next;
        }

        const currentValue = this.tail.value;

        this.tail = currentNode;
        this.tail.next = null;

        this.length--;

        return {
            value: currentValue,
            next: this.tail.next
        }
    }

    // Time O(1)
    // Space O(1)
    peekAtHead() {
        if (!this.head) {
            return undefined;
        }

        return this.head.value;
    }

    // Time O(N)
    // Space O(1)
    print() {
        let current = this.head;

        if(!current) {
            return;
        }

        while (current) {
            console.log(current.value);
            current = current.next;
        }
    }
}

module.exports = {
    SinglyLinkedList,
    SinglyLinkedNode
}

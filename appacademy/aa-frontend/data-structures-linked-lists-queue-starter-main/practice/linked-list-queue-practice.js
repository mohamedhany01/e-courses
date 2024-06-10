// Basic implementation of Nodes and Linked List for you to use

class SinglyLinkedNode {
    constructor(val) {
        this.value = val;
        this.next = null;
    }
}

class SinglyLinkedList {
    constructor(head = null) {
        this.head = head;
        this.length = 0;
    }

    addToTail(val) {
        let newNode = new SinglyLinkedNode(val);

        if (!this.head) {
            this.head = newNode;
            this.length++;
            return this.head;
        }

        let curr = this.head;
        while (curr.next) {
            curr = curr.next;
        }

        curr.next = newNode;
        this.length++;

        return this.head;
    }

    // Time O(1)
    // Space O(1)
    listLength() {
        return this.length;
    }

    // Time O(N)
    // Space O(1)
    sumOfNodes() {
        let total = 0;

        if (!this.head) {
            return total;
        }

        let currentNode = this.head;

        while (currentNode.next) {
            total += currentNode.value;
            currentNode = currentNode.next;
        }

        total += currentNode.value;

        return total;
    }

    // Time O(N)
    // Space O(1)
    averageValue() {
        return this.sumOfNodes() / this.length;
    }

    // Time O(N)
    // Space O(1)
    findNthNode(n) {
        let result = null;

        if (!this.head) {
            return result;
        }

        if (n > this.length) {
            return result;
        }

        let currentNode = this.head;

        let counter = 0;

        while (currentNode.next) {
            if (counter === n) {
                return result = currentNode;
            }

            currentNode = currentNode.next;
            counter++;
        }

        return result;
    }

    // Time O(N)
    // Space O(1)
    findMid() {
        if (!this.head) {
            return null;
        }

        if (this.length === 1) {
            return this.head.value;
        }

        const mid = Math.ceil(this.length / 2) - 1;

        let currentNode = this.head;

        let counter = 0;

        while (currentNode.next) {
            if (counter === mid) {
                return currentNode;
            }

            currentNode = currentNode.next;
            counter++;
        }

        return null;
    }

    // Time O(N)
    // Space O(N)
    reverse() {
        this.head = this._reverseUtil(this.head);
        return this;
    }

    _reverseUtil(node) {

        if (!node) {
            return null;
        }

        if (!node.next) {
            return node;
        }

        let newHead = this._reverseUtil(node.next);
        node.next.next = node;
        node.next = null;

        return newHead;
    }

    // Time O(N)
    // Space O(1)
    reverseInPlace() {
        if (!this.head) {
            return null;
        }

        if (this.length === 1) {
            return this.head;
        }

        let currentNode = this.head;
        let nextNode = null;
        let previousNode = null;

        while (currentNode) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        this.head = previousNode;

        return this;
    }
}

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

    addToTail(val) {
        let newNode = new DoublyLinkedNode(val);

        if (!this.head) {
            this.head = newNode;
            this.tail = newNode;
            this.length++;
            return this.head;
        }

        this.tail.next = newNode;
        newNode.prev = this.tail;
        this.tail = newNode;
        this.length++;

        return this.head;
    }

    // Time O(N)
    // Space O(1)
    findMid() {
        if (!this.head) {
            return null;
        }

        if (this.length === 1) {
            return this.head.value;
        }

        const mid = Math.ceil(this.length / 2) - 1;

        let currentNode = this.head;

        let counter = 0;

        while (currentNode.next) {
            if (counter === mid) {
                return currentNode;
            }

            currentNode = currentNode.next;
            counter++;
        }

        return null;
    }

    // Time O(N)
    // Space O(N)
    reverse() {
        this.head = this._reverseUtil(this.head);
        return this;
    }

    _reverseUtil(node) {

        if (!node) {
            return null;
        }

        if (!node.next) {
            node.prev = null;
            return node;
        }

        let newHead = this._reverseUtil(node.next);
        node.prev = node.next;
        node.next.next = node;
        node.next = null;

        return newHead;
    }


    // Time O(N)
    // Space O(1)
    reverseInPlace() {
        if (!this.head) {
            return null;
        }

        if (this.length === 1) {
            return this.head;
        }

        let currentNode = this.head;
        let nextNode = null;
        let previousNode = null;

        while (currentNode) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        this.head = previousNode;

        return this;
    }
}

module.exports = {
    SinglyLinkedNode,
    SinglyLinkedList,
    DoublyLinkedNode,
    DoublyLinkedList
}

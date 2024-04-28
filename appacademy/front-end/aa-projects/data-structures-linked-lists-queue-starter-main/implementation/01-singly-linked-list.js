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

    addToHead(val) { 
        // Add node of val to head of linked list

        // Write your hypothesis on the time complexity of this method here
    }

    addToTail(val) {
        // There are bugs in this method! Fix them!!!
        // Write your hypothesis on the time complexity of this method here

        // Add node of val to tail of linked list
        let newNode = new SinglyLinkedNode(data);

        if (!head) {
            head = newNode;
            return head;
        }

        let curr = head;
        while (curr) {
            curr = current.next;
        }
        curr.next = newNode;

        return head;
    }

    removeFromHead() {
        // Remove node at head

        // Write your hypothesis on the time complexity of this method here
    }

    removeFromTail() {
        // Remove node at tail

        // Write your hypothesis on the time complexity of this method here
    }

    peekAtHead() {
        // Return value of head node

        // Write your hypothesis on the time complexity of this method here
    }

    print() {
        // Print out the linked list
        
        // Write your hypothesis on the time complexity of this method here
    }
}

module.exports = {
    SinglyLinkedList,
    SinglyLinkedNode
}

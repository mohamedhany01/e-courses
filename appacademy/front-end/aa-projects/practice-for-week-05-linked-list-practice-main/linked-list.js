class LinkedListNode {
  constructor(val) {
    this.value = val;
    this.next = null;
  }
}

class LinkedList {
  constructor() {
    this.head = null;
    this.tail = undefined;
    this.length = 0;
  }

  addToHead(val) {
    if (!this.head) {
      this.head = new LinkedListNode(val);
      this.length++;
      return
    }

    const newNode = new LinkedListNode(val);
    newNode.next = this.head;
    this.head = newNode;
    this.length++;

  }

  addToTail(val) {

    if (!this.head) {
      this.addToHead(val);

      this.tail = this.head;

      return;
    }

    const newNode = new LinkedListNode(val);

    this.tail.next = newNode;

    this.tail = newNode;

    this.length++;
  }

  // You can use this function to help debug
  print() {
    let current = this.head;

    while (current) {
      process.stdout.write(`${current.value} -> `);
      current = current.next;
    }

    console.log("NULL");
  }
}

module.exports = LinkedList;
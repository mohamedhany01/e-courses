const LinkedList = require('./linked-list.js');
const DoublyLinkedList = require('./doubly-linked-list.js');

/*
Construct a timing test to verify the time complexities of `addToHead` and
`addToTail` for both singly and doubly linked lists.
*/

function testLLSmallStress() {
    console.log("testLLSmallStress started");

    let ll = new LinkedList();

    console.time("LL - addToHead");
    for (let i = 0; i < 50000; i++) {
        ll.addToHead(i);
    }
    console.timeEnd("LL - addToHead");

    ll = new LinkedList();
    console.time("LL - addToTail");
    for (let i = 0; i < 50000; i++) {
        ll.addToTail(i);
    }
    console.timeEnd("LL - addToTail");

    console.log("========================================\n");
}

function testLLBigStress() {
    console.log("testLLBigStress started");

    let ll = new LinkedList();

    console.time("LL - addToHead");
    for (let i = 0; i < 10000000; i++) {
        ll.addToHead(i);
    }
    console.timeEnd("LL - addToHead");

    ll = new LinkedList();
    console.time("LL - addToTail");
    for (let i = 0; i < 10000000; i++) {
        ll.addToTail(i);
    }
    console.timeEnd("LL - addToTail");

    console.log("========================================\n");
}

function testDLSmallStress() {
    console.log("testDLSmallStress started");

    let ll = new DoublyLinkedList();

    console.time("DL - addToHead");
    for (let i = 0; i < 50000; i++) {
        ll.addToHead(i);
    }
    console.timeEnd("DL - addToHead");

    ll = new DoublyLinkedList();
    console.time("DL - addToTail");
    for (let i = 0; i < 50000; i++) {
        ll.addToTail(i);
    }
    console.timeEnd("DL - addToTail");

    console.log("========================================\n");
}

function testDlBigStress() {
    console.log("testDLBigStress started");

    let ll = new DoublyLinkedList();

    console.time("DL - addToHead");
    for (let i = 0; i < 10000000; i++) {
        ll.addToHead(i);
    }
    console.timeEnd("DL - addToHead");

    ll = new DoublyLinkedList();
    console.time("DL - addToTail");
    for (let i = 0; i < 10000000; i++) {
        ll.addToTail(i);
    }
    console.timeEnd("DL - addToTail");

    console.log("========================================\n");
}

testLLSmallStress();
testDLSmallStress();

testLLBigStress();
testDlBigStress();
package bstmap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;
    private Node root;

    public BSTMap() {
        this.size = size;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return getRecursively(this.root, key);
    }

    private V getRecursively(Node node, K key) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) == 0) {
            return node.value;
        } else if (key.compareTo(node.key) > 0) {
            return getRecursively(node.right, key);
        } else if (key.compareTo(node.key) < 0) {
            return getRecursively(node.left, key);
        } else {
            return null;
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        this.root = addRecursively(this.root, key, value);
    }

    private Node addRecursively(Node node, K key, V value) {
        if (node == null) {
            return createNewNode(key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = addRecursively(node.right, key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = addRecursively(node.left, key, value);
        } else {
            // If the key exists, just overwrite its value
            node.value = value;
            return node;
        }
        return node;
    }

    private Node createNewNode(K key, V value) {
        Node newNode = new Node(key, value);
        size++;
        return newNode;
    }

    public void printInOrder() {
        traverseInorderRecursively(this.root);
    }

    private void traverseInorderRecursively(Node node) {
        if (node != null) {
            traverseInorderRecursively(node.left);
            System.out.print(node.key + " ");
            traverseInorderRecursively(node.right);
        }
    }

    // Unsupported API
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    // Unsupported API
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    // Unsupported API
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    // Unsupported API
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        private final K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public V getValue() {
            return value;
        }
    }
}

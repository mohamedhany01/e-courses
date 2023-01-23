package bstmap;

import java.util.Iterator;
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

    @Override
    public V remove(K key) {
        V value = get(key);

        // Delete and restructure the tree
        this.root = getNewStructure(root, key);
        return value;
    }

    private Node getMaxNode(Node node) {
        if (node == null) {
            return null;
        } else if (node.right == null) {
            return node;
        } else {
            return getMaxNode(node.right);
        }
    }

    private Node getNewStructure(Node node, K key) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) > 0) {
            // Search in right side
            node.right = getNewStructure(node.right, key);
        } else if (key.compareTo(node.key) < 0) {
            // Search in left side
            node.left = getNewStructure(node.left, key);
        } else {
            // We found the target node, so we have 3 cases
            if (node.left == null) {
                /*
                 * Return child node of target node:
                 *   - in case one is null in right side
                 *   - in case two, it has a node will be relinked
                 * */
                size--;
                return node.right;
            } else if (node.right == null) {
                /*
                 * Return child node of target node:
                 *   - in case one is null in left side
                 *   - in case two, it has a node will be relinked
                 * */
                size--;
                return node.left;
            } else {
                Node maxOfTarget = getMaxNode(node.left);
                node.key = maxOfTarget.key;
                node.left = getNewStructure(maxOfTarget, maxOfTarget.key);
            }
        }
        return node;
    }

    @Override
    public V remove(K key, V value) {
        V nodeValue = get(key);

        // Delete and restructure the tree
        this.root = getNewStructure(root, key);
        return nodeValue;
    }

    // Unsupported API
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        private K key;
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

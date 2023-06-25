package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;
    private Node root;

    public BSTMap() {
        this.size = 0;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /*
     *  You can't use `get()` method to check for key, why?
     *   - The get() method returns the value associated with a given key,
     *   or null if the key is not present in the map. So, if a key is present
     *   in the map but its value is null, the get() method will return null which can be misleading.
     * */
    @Override
    public boolean containsKey(K key) {
        Node current = this.root;
        while (current != null) {
            int comparisonValue = key.compareTo(current.key);
            if (comparisonValue > 0) {
                current = current.right;
            } else if (comparisonValue < 0) {
                current = current.left;
            } else {
                return true;
            }
        }
        return false;
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

    @Override
    public Set<K> keySet() {
        // If you used `HashSet` won't print the elements in-order, but `TreeSet` will
        Set<K> mapKeys = new TreeSet<>();
        extractKeys(this.root, mapKeys);
        return mapKeys;
    }

    private void extractKeys(Node node, Set<K> set) {
        if (node != null) {
            set.add(node.key);
            extractKeys(node.left, set);
            extractKeys(node.right, set);
        }
    }

    @Override
    public V remove(K key) {
        V value = get(key);

        // Delete and restructure the tree
        this.root = getNewStructure(root, key);
        return value;
    }

    /*
     * It's important to note that this method
     * can only be used to get the max key if the keys are unique,
     * if the keys are not unique the method will return the rightmost key,
     * which is not necessary the max key.
     * */
    private Node getMaxNode(Node node) {
        if (node == null || this.size == 0) {
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

    @Override
    public Iterator<K> iterator() {
        return new MapKeysIterator(this.root);
    }


    // In-order "LRR" iterator
    private class MapKeysIterator implements Iterator<K> {
        private final Stack<Node> nodesTracker = new Stack<>();

        public MapKeysIterator(Node node) {
            if (node != null) {
                // Put root and its left side in the stack
                // In-order
                pushLeft(node);

                // Reversed order
                // pushRight(node);
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        private void pushLeft(Node node) {
            while (node != null) {
                nodesTracker.push(node);
                node = node.left;
            }
        }

        private void pushRight(Node node) {
            while (node != null) {
                nodesTracker.push(node);
                node = node.right;
            }
        }

        @Override
        public boolean hasNext() {
            return !nodesTracker.isEmpty();
        }

        @Override
        public K next() {
            Node currentNode = nodesTracker.pop();

            // For each right node "if it exits" in the stack track their left side as well
            // In-order
            pushLeft(currentNode.right);

            // Reversed order
            // pushRight(currentNode.left);
            return currentNode.key;
        }
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
    }
}

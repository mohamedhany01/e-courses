package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    // You should probably define some more!
    private final double maxLoad;
    private int initialSize;

    /**
     * Constructors
     */
    public MyHashMap() {
        this.size = 0;
        this.initialSize = 16;
        this.maxLoad = 0.75;
        this.buckets = this.createTable(this.initialSize);
    }

    public MyHashMap(int initialSize) {
        this.size = 0;
        this.initialSize = initialSize;
        this.maxLoad = 0.75;
        this.buckets = this.createTable(this.initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = 0;
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        this.buckets = this.createTable(this.initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    @Override
    public void clear() {

        // Reset the buckets again
        for (int i = 0; i < this.buckets.length; i++) {
            this.buckets[i] = createBucket();
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getHash(key);
        Collection<Node> bucket = this.buckets[index];

        if (bucket == null) {
            return false;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = getHash(key);
        Collection<Node> bucket = this.buckets[index];

        // In case the bucket is empty, just return empty
        if (bucket == null) {
            return null;
        }

        // Else it's exit, and we need to loop over the bucket to find it
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (isReachedMaxLoad()) {
            resize(2);
        }

        int index = getHash(key);
        Collection<Node> bucket = this.buckets[index];

        // In case the bucket is null create a new one
        if (bucket == null) {
            bucket = createBucket();
            this.buckets[index] = bucket;
        }

        // In case the key is already exits, just replace its value and exit
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // In case the key isn't exist just a new node to the bucket
        bucket.add(createNode(key, value));

        this.size++;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> bucket : this.buckets) {

            if (bucket == null) continue;

            for (Node node : bucket) {
                set.add(node.key);
            }
        }

        return set;
    }

    @Override
    public V remove(K key) {
        int index = getHash(key);
        Collection<Node> bucket = this.buckets[index];

        if (bucket == null) {
            return null;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                V val = node.value;
                bucket.remove(node);
                this.size--;
                return val;
            }
        }

        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = getHash(key);
        Collection<Node> bucket = this.buckets[index];

        if (bucket == null) {
            return null;
        }

        for (Node node : bucket) {
            if (node.key.equals(key) && get(key).equals(value)) {
                V val = node.value;
                bucket.remove(node);
                this.size--;
                return val;
            }
        }

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int getHash(K key) {
        return Math.floorMod(key.hashCode(), this.initialSize) ;
    }

    private boolean isReachedMaxLoad() {
        int maxLoad = this.size / this.initialSize;
        return maxLoad >= this.maxLoad;
    }

    private void resize(int size) {
        this.initialSize = size * this.initialSize;
        Collection<Node>[] oldBuckets = this.buckets;
        this.buckets = createTable(initialSize);
        this.size = 0;
        for (Collection<Node> bucket : oldBuckets) {
            if (bucket == null) continue;
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
}

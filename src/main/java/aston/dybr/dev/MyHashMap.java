package aston.dybr.dev;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final int RESIZE_FACTOR = 2;

    private static class Node<K, V> {

        private final K key;

        private V value;

        private Node<K, V> next;

        private Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private Node(Node<K, V> next) {
            this.next = next;
            this.key = null;
        }
    }

    private Node<K, V>[] buckets;
    private int size;

    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
    }

    public void put(K key, V value) {
        put(key, value, false);
    }

    private void put(K key, V value, boolean resizing) {

        int index = calculateIndex(key);

        Node<K, V> newNode = new Node<>(key, value, null);
        Node<K, V> dummy = new Node<>(buckets[index]);
        Node<K, V> current = dummy;

        while (current.next != null && !current.next.key.equals(key)) {
            current = current.next;
        }

        if (current.next == null) {
            current.next = newNode;
            size++;
            buckets[index] = dummy.next;

            if (size >= buckets.length * LOAD_FACTOR && !resizing) {
                resize();
            }
            return;
        }

        current.next.value = value;
    }

    public V get(K key) {

        int index = calculateIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null && !current.key.equals(key)) {
            current = current.next;
        }

        return current != null ? current.value : null;
    }

    public void remove(K key) {
        int index = calculateIndex(key);

        Node<K, V> dummy = new Node<>(buckets[index]);
        Node<K, V> current = dummy;

        while (current.next != null && !current.next.key.equals(key)) {
            current = current.next;
        }

        if (current.next != null) {

            current.next = current.next.next;
            buckets[index] = dummy.next;

            size--;
        }
    }

    private void resize() {
        Node<K, V>[] oldBuckets = buckets;

        buckets = new Node[buckets.length * RESIZE_FACTOR];
        size = 0;

        for (Node<K, V> node : oldBuckets) {

            while (node != null) {
                put(node.key, node.value, true);
                node = node.next;
            }
        }
    }

    private int calculateIndex(K key) {
        int hash = key.hashCode();

        hash ^= hash >>> 16;

        return hash & (buckets.length - 1);
    }

    public int size() {
        return size;
    }

    public void printBuckets() {

        int i = 1;
        for (Node<K, V> node : buckets) {

            int size = 0;

            System.out.printf("Bucket %d: [", i++);

            while (node != null) {
                System.out.print(get(node.key));
                size++;
                node = node.next;
            }

            System.out.printf("]  size %d%n", size);
        }
    }
}

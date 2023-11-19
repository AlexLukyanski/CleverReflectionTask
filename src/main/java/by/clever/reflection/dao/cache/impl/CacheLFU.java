package by.clever.reflection.dao.cache.impl;

import by.clever.reflection.dao.cache.Cache;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * LFU implementation of cash
 *
 * @param <K> - key for the cache
 * @param <V> - value for the cache
 */
public class CacheLFU<K, V> implements Cache<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;
    private Map<K, Node<K, V>> map;
    private final int capacity;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    public CacheLFU(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<K, Node<K, V>>();
    }

    /**
     * @param key   Given key for the cache
     * @param value Given value for the cache
     */
    @Override
    public void put(K key, V value) {

        writeLock.lock();

        try {
            if (map.containsKey(key)) {
                Node<K, V> item = map.get(key);
                item.setValue(value);
                item.setFrequency(item.getFrequency() + 1);
                removeNode(item);
                addNodeWithUpdatedFrequency(item);
            } else {
                if (map.size() >= capacity) {
                    map.remove(head.getKey());
                    removeNode(head);
                }

                Node<K, V> node = new Node<K, V>(key, value, 1);
                addNodeWithUpdatedFrequency(node);
                map.put(key, node);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * @param key Given key to value in the cache
     * @return Optional of value in the cache
     */
    @Override
    public Optional<V> get(K key) {

        readLock.lock();

        try {
            if (map.get(key) == null) {
                return Optional.empty();
            }

            Node<K, V> item = map.get(key);
            removeNode(item);
            item.setFrequency(item.getFrequency() + 1);
            addNodeWithUpdatedFrequency(item);

            Optional<V> value = Optional.of(item.getValue());
            return value;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * @param key Key of the value in cash that need to be removed
     */
    @Override
    public void remove(K key) {

        writeLock.lock();

        try {
            Node<K, V> item = map.get(key);
            removeNode(item);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Clear cash
     */
    @Override
    public void clear() {

        writeLock.lock();

        try {
            head = null;
            tail = null;
            map = null;
        } finally {
            writeLock.unlock();
        }
    }

    private void removeNode(Node<K, V> node) {

        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
    }

    private void addNodeWithUpdatedFrequency(Node<K, V> node) {

        if (tail != null && head != null) {
            Node<K, V> temp = head;
            while (temp != null) {
                if (temp.getFrequency() > node.getFrequency()) {
                    if (temp == head) {
                        node.setNext(temp);
                        temp.setPrev(node);
                        head = node;
                        break;
                    } else {
                        node.setNext(temp);
                        node.setPrev(temp.getPrev());
                        temp.getPrev().setNext(node);
                        node.setPrev(temp.getPrev());
                        break;
                    }
                } else {
                    temp = temp.getNext();
                    if (temp == null) {
                        tail.setNext(node);
                        node.setPrev(tail);
                        node.setNext(null);
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            tail = node;
            head = tail;
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    public static class Node<K, V> {

        private K key;
        private V value;
        private int frequency;
        private Node<K, V> prev;
        private Node<K, V> next;

        public Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }
}

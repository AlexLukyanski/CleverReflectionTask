package by.clever.reflection.dao.cache.impl;

import by.clever.reflection.dao.cache.Cache;
import by.clever.reflection.dao.cache.exception.CacheException;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * LRU implementation of cash
 *
 * @param <K> - key for the cache
 * @param <V> - value for the cache
 */
public class CacheLRU<K, V> implements Cache<K, V> {

    private Queue<K> queue = new ConcurrentLinkedQueue<K>();
    private Map<K, V> map = new ConcurrentHashMap<K, V>();
    private final int capacity;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    public CacheLRU(int capacity) {
        this.capacity = capacity;
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
                queue.remove(key);
            }
            if (queue.size() == capacity) {
                K queueKey = queue.poll();
                map.remove(queueKey);
            }
            queue.add(key);
            map.put(key, value);
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
            V value;
            if (map.containsKey(key)) {
                queue.remove(key);
                queue.add(key);
                value = map.get(key);
                Optional<V> optionalValue = Optional.of(value);
                return optionalValue;
            } else {
                return Optional.empty();
            }
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
            if (map.containsKey(key)) {
                map.remove(key);
                queue.remove(key);
            } else {
                throw new CacheException("There is no such element in cache");
            }
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
            queue = null;
            map = null;
        } finally {
            writeLock.unlock();
        }
    }
}

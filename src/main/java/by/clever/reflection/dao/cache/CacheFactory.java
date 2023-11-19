package by.clever.reflection.dao.cache;

import by.clever.reflection.dao.cache.impl.CacheLFU;
import by.clever.reflection.dao.cache.impl.CacheLRU;

import java.util.HashMap;
import java.util.Map;


/**
 * Factory of caches
 *
 * @param <K> - key for the cache
 * @param <V> - value for the cache
 */
public final class CacheFactory<K, V> {

    private final Map<CacheName, Cache<K, V>> cashes = new HashMap<>();

    public CacheFactory(int cacheCapacity) {

        cashes.put(CacheName.LRU, new CacheLRU<K, V>(cacheCapacity));
        cashes.put(CacheName.LFU, new CacheLFU<K, V>(cacheCapacity));
    }

    /**
     * @param cacheName Name of requested cache
     * @return Requested cache object
     */
    public Cache<K, V> getCache(String cacheName) {

        CacheName name = CacheName.valueOf(cacheName);
        Cache<K, V> cache = cashes.get(name);
        return cache;
    }
}

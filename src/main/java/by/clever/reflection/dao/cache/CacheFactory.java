package by.clever.reflection.dao.cache;

import by.clever.reflection.dao.cache.impl.CacheLFU;
import by.clever.reflection.dao.cache.impl.CacheLRU;

import java.util.HashMap;
import java.util.Map;

public final class CacheFactory<K, V> {

    private final Map<CacheName, Cache<K, V>> cashes = new HashMap<>();

    public CacheFactory(int cacheCapacity) {

        cashes.put(CacheName.LRU, new CacheLRU<K, V>(cacheCapacity));
        cashes.put(CacheName.LFU, new CacheLFU<K, V>(cacheCapacity));
    }

    public Cache<K, V> getCache(String cacheName) {

        CacheName name = CacheName.valueOf(cacheName);
        Cache<K, V> cache = cashes.get(name);
        return cache;
    }
}

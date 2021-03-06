package com.rohan90.majdoor.db.in_memory;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * For now a datacache held by the service,
 * this should ideally be an external service
 */
@Component
public class CustomCacheClient implements ICacheClient {
    static Map<String, Object> cache = Collections.synchronizedMap(new WeakHashMap<String, Object>());

    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    @Override
    public int count() {
        return cache.size();
    }
}

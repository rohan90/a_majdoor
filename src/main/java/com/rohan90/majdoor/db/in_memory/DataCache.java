package com.rohan90.majdoor.db.in_memory;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class DataCache {
    static Map<String, Object> cache = Collections.synchronizedMap(new WeakHashMap<String, Object>());

    public static Object get(String key) {
        return cache.get(key);
    }

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static void clear() {
        cache.clear();
    }

    public static boolean contains(String key) {
        return cache.containsKey(key);
    }
}

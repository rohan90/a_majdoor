package com.rohan90.majdoor.db.in_memory;
/**
 * contract between dataStore accessed by all schedulers
 * so as to have synchronization of jobs to be executed.
 *
 * For now a datacache held by the service,
 *
 * todo could expand this to a redis contract for redisSet etc
 */
public interface ICacheClient {
    Object get(String key);

    void put(String key, Object value);

    void clear();

    boolean contains(String key);
}

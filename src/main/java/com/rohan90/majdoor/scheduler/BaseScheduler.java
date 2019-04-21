package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.db.in_memory.ICacheClient;
import com.rohan90.majdoor.db.persistence.IDbClient;

public abstract class BaseScheduler {
    static final int DEFAULT_THREAD_POOL_SIZE = 1;

    abstract void configure(int parallelism, long pollDelay, IDbClient dbClient, ICacheClient cacheClient);
}

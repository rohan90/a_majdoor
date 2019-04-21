package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.db.in_memory.ICacheClient;
import com.rohan90.majdoor.db.persistence.IDbClient;

public interface IScheduler {
    int DEFAULT_THREAD_POOL_SIZE = 1;

    void configure(int parallelism, long pollDelay);

    void configure(int parallelism, long pollDelay, IDbClient dbClient, ICacheClient cacheClient);

    void start();

    void stop();

    void identity(String name);

    void runTasks();
}

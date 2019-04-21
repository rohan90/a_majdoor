package com.rohan90.majdoor.scheduler;

public abstract class BaseScheduler {
    static final int DEFAULT_THREAD_POOL_SIZE = 1;

    abstract void configure(int parralellism, long pollDelay);
}

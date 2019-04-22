package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.scheduler.events.TaskUpdateStatusEvent;

public interface IScheduler {
    int DEFAULT_THREAD_POOL_SIZE = 1;

    void configure(int parallelism, long pollDelay);

    void identity(String name);

    String identify();

    void start();

    void stop();

    void runTasks();

    void updateTask(TaskUpdateStatusEvent event);
}

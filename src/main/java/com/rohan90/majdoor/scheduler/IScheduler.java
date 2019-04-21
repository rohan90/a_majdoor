package com.rohan90.majdoor.scheduler;

public interface IScheduler {
    void start();
    void stop();
    void identity(String name);
    void runTasks();
}

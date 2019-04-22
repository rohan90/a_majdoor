package com.rohan90.majdoor.executor.runners;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.scheduler.SchedulerImpl;

/**
 * Blueprint for XTaskRunners
 */
public abstract class TaskRunner implements Runnable {
    protected SchedulerImpl scheduler;
    protected TaskDTO task;

    public TaskRunner(TaskDTO task, SchedulerImpl scheduler) {
        this.task = task;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        throw new RuntimeException("Not implemented");
    }
}

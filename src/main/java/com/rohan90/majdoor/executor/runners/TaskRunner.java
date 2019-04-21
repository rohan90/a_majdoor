package com.rohan90.majdoor.executor.runners;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import org.springframework.context.ApplicationEventPublisher;

public abstract class TaskRunner implements Runnable {
    protected TaskDTO task;
    protected String schedulerName;
    protected ApplicationEventPublisher publisher;

    public TaskRunner(TaskDTO task, String schedulerName, ApplicationEventPublisher publisher) {
        this.task = task;
        this.schedulerName = schedulerName;
        this.publisher = publisher;
    }

    @Override
    public void run() {
        throw new RuntimeException("Not implemented");
    }
}

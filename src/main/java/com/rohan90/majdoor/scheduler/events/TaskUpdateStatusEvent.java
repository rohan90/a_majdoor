package com.rohan90.majdoor.scheduler.events;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.context.ApplicationEvent;

public class TaskUpdateStatusEvent extends ApplicationEvent {
    private final TaskDTO task;
    private final String schedulerName;
    private final TaskStatus status;

    public TaskUpdateStatusEvent(Object source, String schedulerName, TaskStatus status, TaskDTO t) {
        super(source);
        this.schedulerName = schedulerName;
        this.status = status;
        this.task = t;
    }

    public TaskDTO getTask() {
        return task;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskUpdateStatusEvent{" +
                "taskId=" + task.getId() +
                ", schedulerName='" + schedulerName + '\'' +
                ", status=" + status +
                '}';
    }
}

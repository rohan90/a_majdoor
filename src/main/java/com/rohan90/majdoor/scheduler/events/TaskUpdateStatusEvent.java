package com.rohan90.majdoor.scheduler.events;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.context.ApplicationEvent;

public class TaskUpdateStatusEvent extends ApplicationEvent {
    private final TaskDTO task;
    private final TaskStatus status;

    public TaskUpdateStatusEvent(Object source ,TaskStatus status, TaskDTO t) {
        super(source);
        this.status = status;
        this.task = t;
    }

    public TaskDTO getTask() {
        return task;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskUpdateStatusEvent{" +
                "taskId=" + task.getId() +
                ", status=" + status +
                '}';
    }
}

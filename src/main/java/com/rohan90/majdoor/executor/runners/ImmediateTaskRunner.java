package com.rohan90.majdoor.executor.runners;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import com.rohan90.majdoor.executor.operators.Operator;
import com.rohan90.majdoor.executor.operators.OperatorFactory;
import com.rohan90.majdoor.scheduler.events.TaskUpdateStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Date;

public class ImmediateTaskRunner extends TaskRunner {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public ImmediateTaskRunner(TaskDTO t, String name, ApplicationEventPublisher publisher) {
        super(t, name,publisher);
    }

    @Override
    public void run() {
        try {
            LOG.info("\nexecuting task with Id = {} - Name = {} , at time {}", task.getId(), task.getName(), new Date());
            Operator operator = OperatorFactory.get(task.getOperator());
            operator.execute();
            publisher.publishEvent(new TaskUpdateStatusEvent(this, schedulerName, TaskStatus.COMPLETED, task));

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Error executing task with Id = {} - Name =  {} , at time {}", task.getId(), task.getName(), new Date());
            publisher.publishEvent(new TaskUpdateStatusEvent(this, schedulerName, TaskStatus.FAILED, task));
        }
    }

}
package com.rohan90.majdoor.executor.runners;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import com.rohan90.majdoor.executor.operators.Operator;
import com.rohan90.majdoor.executor.operators.OperatorFactory;
import com.rohan90.majdoor.scheduler.SchedulerImpl;
import com.rohan90.majdoor.scheduler.events.TaskUpdateStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class FutureTaskRunner extends TaskRunner {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public FutureTaskRunner(TaskDTO t, SchedulerImpl name) {
        super(t, name);
    }

    @Override
    public void run() {
        try {
            LOG.info("\nStarting task with Id = {} - Name = {} , at time {}", task.getId(), task.getName(), new Date());
            Operator operator = OperatorFactory.get(task.getOperator());
            operator.execute();
            scheduler.updateTask(new TaskUpdateStatusEvent(this, scheduler, TaskStatus.COMPLETED, task));

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Error Starting task with Id = {} - Name =  {} , at time {}", task.getId(), task.getName(), new Date());
            scheduler.updateTask(new TaskUpdateStatusEvent(this, scheduler, TaskStatus.FAILED, task));
        }
    }
}

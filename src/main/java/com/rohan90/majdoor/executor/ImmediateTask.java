package com.rohan90.majdoor.executor;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.executor.operators.Operator;
import com.rohan90.majdoor.executor.operators.OperatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ImmediateTask implements Runnable {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final TaskDTO task;

    public ImmediateTask(TaskDTO t) {
        this.task = t;
    }

    @Override
    public void run() {
        try {
            LOG.info("\nStarting task with Id = {} - Name = {} , at time {}", task.getId(), task.getName(), new Date());
            Operator operator = OperatorFactory.get(task.getOperator());
            operator.execute();

        } catch (Exception e) {
            LOG.info("Error Starting task with Id = {} - Name =  {} , at time {}", task.getId(), task.getName(), new Date());
            e.printStackTrace();
        }
    }
}

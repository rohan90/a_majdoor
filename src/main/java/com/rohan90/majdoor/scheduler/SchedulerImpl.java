package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.executor.ImmediateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
public class SchedulerImpl extends BaseScheduler implements IScheduler {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String name;
    private int poolSize = DEFAULT_THREAD_POOL_SIZE;
    private ScheduledExecutorService executor;

    @Autowired
    DataPoller poller;


    @Override
    public void configure(int parralellism, long pollDelay) {
        poolSize = parralellism;
        poller.setup(pollDelay, this);
    }


    @Override
    public void identity(String name) {
        this.name = name;
    }

    @Override
    public void start() {
        poller.start();
        executor = Executors.newScheduledThreadPool(poolSize);
        LOG.info("started scheduler {}, time is ", this.name, new Date());

    }

    @Override
    public void stop() {
        poller.stopPolling();
        executor.shutdown();
    }

    @Override
    public void runTasks() {
        scheduleOrRunTasks();
    }

    private void scheduleOrRunTasks() {
        List<TaskDTO> tasks = poller.getTasks();

        tasks.forEach(t -> {
            if (ScheduleType.IMMEDIATE.equals(t.getScheduleMeta().getType()))
                executor.schedule(new ImmediateTask(t), 1, TimeUnit.SECONDS);
        });
    }
}

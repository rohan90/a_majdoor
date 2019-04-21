package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.db.IDBClient;
import com.rohan90.majdoor.executor.FutureTaskRunner;
import com.rohan90.majdoor.executor.ImmediateTaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private DataPoller poller;
    private long pollDelay;
    private IDBClient dbClient;


    @Override
    public void configure(int parralellism, long pollDelay, IDBClient dbClient) {
        poolSize = parralellism;
        this.pollDelay = pollDelay;
        this.dbClient = dbClient;
    }


    @Override
    public void identity(String name) {
        this.name = name;
    }

    @Override
    public void start() {
        poller = new DataPoller(this, dbClient, pollDelay);
        poller.setName(name + "-poller");
        poller.start();
        executor = Executors.newScheduledThreadPool(poolSize);
        LOG.info("started scheduler {}, time is ", this.name, new Date());

    }

    @Override
    public void stop() {
        poller.stopPolling();
        poller.interrupt();
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
                executor.schedule(new ImmediateTaskRunner(t), 1, TimeUnit.SECONDS);
            else {
                long delay = TaskDTO.getDelayInMillis(t.getScheduleMeta().getValue());
                executor.schedule(new FutureTaskRunner(t), delay, TimeUnit.SECONDS);
            }
        });
    }
}

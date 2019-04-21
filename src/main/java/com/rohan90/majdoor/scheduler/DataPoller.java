package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.db.persistence.IDbClient;

import java.util.List;

public class DataPoller<T> extends Thread {

    private List<T> data;

    public DataPoller(IScheduler scheduler, IDbClient idbClient, long pollDelay) {
        this.delay = pollDelay;
        this.dbClient = idbClient;
        this.scheduler = scheduler;
    }

    IDbClient dbClient;

    private long delay;
    private boolean stopped;
    private IScheduler scheduler;

    @Override
    public void run() {
        try {
            while (!stopped) {
                if (interrupted())
                    return;

                data = (List<T>) dbClient.getPendingTasks();
                scheduler.runTasks();
                sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //this should be a stream, but for now keeping it simple
    public List<T> getTasks() {
        return data;
    }

    public void stopPolling() {
        this.stopped = true;
        interrupt();
    }


}

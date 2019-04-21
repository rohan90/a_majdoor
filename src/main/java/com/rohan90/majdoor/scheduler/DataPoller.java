package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.db.persistence.IDBClient;

import java.util.List;

public class DataPoller<T> extends Thread {

    private List<T> data;

    public DataPoller(IScheduler scheduler, IDBClient idbClient, long pollDelay) {
        this.delay = pollDelay;
        this.idbClient = idbClient;
        this.scheduler = scheduler;
    }

    IDBClient idbClient;

    private long delay;
    private boolean stopped;
    private IScheduler scheduler;

    @Override
    public void run() {
        try {
            while (!stopped) {
                if (interrupted())
                    return;

                data = (List<T>) idbClient.getPendingTasks();
                scheduler.runTasks();
                sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<T> getTasks() {
        return data;
    }

    public void stopPolling() {
        this.stopped = true;
    }


}

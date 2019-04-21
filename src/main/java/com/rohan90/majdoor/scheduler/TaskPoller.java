package com.rohan90.majdoor.scheduler;

import com.rohan90.majdoor.db.IDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DataPoller<T> extends Thread {

    @Autowired
    IDBClient idbClient;

    private List<T> data;
    private long delay;
    private boolean stopped;
    private IScheduler scheduler;

    @Override
    public void run() {
        try {

            while (!stopped) {
                data = (List<T>) idbClient.getTasks();
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

    public void setup(long pollDelay, IScheduler scheduler) {
        this.delay = pollDelay;
        this.scheduler = scheduler;
    }
}

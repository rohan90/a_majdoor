package com.rohan90.majdoor.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("scheduler")
public class SchedulerConfig {
    private int nodes;
    private int parallelism;
    private int pollDelay;

    public int getNodes() {
        return nodes;
    }

    public int getParallelism() {
        return parallelism;
    }

    public int getPollDelay() {
        return pollDelay;
    }
}
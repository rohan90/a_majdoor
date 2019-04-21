package com.rohan90.majdoor.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("majdoor")
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

    public boolean isValid() {
        boolean areNodeCountsProvided = nodes > 0;
        boolean isParallelismCountProvided = parallelism > 0;
        boolean isPollDelayProvided = pollDelay > 0;
        return areNodeCountsProvided && isParallelismCountProvided && isPollDelayProvided;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public void setPollDelay(int pollDelay) {
        this.pollDelay = pollDelay;
    }
}
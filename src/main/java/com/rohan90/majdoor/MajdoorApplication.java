package com.rohan90.majdoor;

import com.rohan90.majdoor.scheduler.IScheduler;
import com.rohan90.majdoor.scheduler.SchedulerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MajdoorApplication implements ApplicationRunner {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IScheduler scheduler;

    @Autowired
    SchedulerConfig config;

    public static void main(String[] args) {
        SpringApplication.run(MajdoorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Stareted application and now intializing schedulers...");

        int nodes = config.getNodes();
        for (int i = 0; i < nodes; i++) {
            scheduler.identity("scheduler-" + UUID.randomUUID().toString());
            scheduler.configure(config.getParallelism(),config.getPollDelay());
            scheduler.start();
        }

    }
}

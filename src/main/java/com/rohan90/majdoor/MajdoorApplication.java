package com.rohan90.majdoor;

import com.rohan90.majdoor.scheduler.IScheduler;
import com.rohan90.majdoor.scheduler.SchedulerConfig;
import com.rohan90.majdoor.scheduler.SchedulerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MajdoorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajdoorApplication.class, args);
    }

}

@Component
class AppStartupRunner implements ApplicationRunner {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Autowired
    SchedulerConfig config;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void run(ApplicationArguments args) {
        if (isTestEnvironment())
            return;

        LOG.info("Started application and now intializing schedulers...");

        if (!config.isValid()) {
            LOG.info("Failed to start application please set properties for majdoor...");
            throw new RuntimeException("Please provide valid scheduler details in properties file eg, nodes,parrelism,pollDelay");
        }

        int nodes = config.getNodes();
        for (int i = 0; i < nodes; i++) {
            IScheduler scheduler = new SchedulerImpl();
            beanFactory.autowireBean(scheduler);
            scheduler.identity("scheduler-" + i + "-" + UUID.randomUUID().toString());
            scheduler.configure(config.getParallelism(), TimeUnit.SECONDS.toMillis(config.getPollDelay()));
            scheduler.start();
        }
    }

    //dont like this, but my test environment was requesting profile for scheduler setup
    //which i did not want to do as i want to create scenarios for my scheduler.
    //for now doing it this way
    private boolean isTestEnvironment() {
        return activeProfile.equalsIgnoreCase("test");
    }
}



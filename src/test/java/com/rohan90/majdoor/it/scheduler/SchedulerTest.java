package com.rohan90.majdoor.it.scheduler;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.utils.rest.RestWrapper;
import com.rohan90.majdoor.scheduler.SchedulerImpl;
import com.rohan90.majdoor.utils.BaseAPITest;
import com.rohan90.majdoor.utils.MockData;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class SchedulerTest extends BaseAPITest {

    @Autowired
    SchedulerImpl scheduler;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldBeAbleToScheduleTasks() {

        int taskCount = 10;
        for (int i = 0; i < taskCount; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }

        scheduler.identity("test-scheduler");
        scheduler.configure(1, TimeUnit.SECONDS.toMillis(5));
        scheduler.start();


        sleep(TimeUnit.SECONDS.toMillis(30));
    }

    @Test
    public void shouldBeAbleToScheduleTasksParallely() {

        int taskCount = 10;
        for (int i = 0; i < taskCount; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }

        scheduler.identity("test-scheduler");
        scheduler.configure(2, TimeUnit.SECONDS.toMillis(5));
        scheduler.start();


        sleep(TimeUnit.SECONDS.toMillis(30));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

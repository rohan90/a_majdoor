package com.rohan90.majdoor.it.scheduler;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.tasks.ITaskRepository;
import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.ScheduleMetaDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.api.utils.rest.RestWrapper;
import com.rohan90.majdoor.db.in_memory.CustomCacheClient;
import com.rohan90.majdoor.db.persistence.SqlClient;
import com.rohan90.majdoor.scheduler.SchedulerImpl;
import com.rohan90.majdoor.utils.BaseAPITest;
import com.rohan90.majdoor.utils.MockData;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class SchedulerTest extends BaseAPITest {

    @Autowired
    SchedulerImpl scheduler;

    @Autowired
    ITaskRepository repository;

    @Autowired
    SqlClient dbClient;

    @Autowired
    CustomCacheClient cacheClient;

    @Before
    public void setUp() {
    }

    @After
    public void cleanUp() {
        repository.deleteAll();
        scheduler.stop();
    }

    @Test
    public void shouldBeAbleToSchedule_ImmediateTasks() {

        int taskCount = 10;
        for (int i = 0; i < taskCount; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }

        scheduler.identity("test-scheduler");
        scheduler.configure(1, TimeUnit.SECONDS.toMillis(5), dbClient, cacheClient);
        scheduler.start();


        sleep(TimeUnit.SECONDS.toMillis(15));
    }

    @Test
    public void shouldBeAbleToSchedule_ImmediateTasks_InParallel() {

        int taskCount = 10;
        for (int i = 0; i < taskCount; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }

        scheduler.identity("test-scheduler");
        scheduler.configure(2, TimeUnit.SECONDS.toMillis(5), dbClient, cacheClient);
        scheduler.start();


        sleep(TimeUnit.SECONDS.toMillis(15));
    }


    @Test
    public void shouldBeAbleToSchedule_FutureTasks() {

        CreateTaskDTO immediateTaskPayload = MockData.getCreateTaskDTO();
        Response response = RestWrapper
                .given()
                .body(immediateTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(201, response.getStatusCode());

        CreateTaskDTO futureTaskPayload = MockData.getCreateTaskDTO(); //a tasks scheduled for ten seconds later
        futureTaskPayload.setScheduleMeta(new ScheduleMetaDTO(ScheduleType.FUTURE, String.valueOf(5)));
        response = RestWrapper
                .given()
                .body(futureTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(201, response.getStatusCode());

        scheduler.identity("test-scheduler");
        scheduler.configure(1, TimeUnit.SECONDS.toMillis(5), dbClient, cacheClient);
        scheduler.start();


        sleep(TimeUnit.SECONDS.toMillis(15));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

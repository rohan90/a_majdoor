package com.rohan90.majdoor.sanity;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.tasks.ITaskRepository;
import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.ScheduleMetaDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDashboardDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import com.rohan90.majdoor.api.utils.rest.RestParser;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SanityTest extends BaseAPITest {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

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
    public void sanityTest1() {
        /**
         * This test is a whole roundabout to the system
         *
         * 1. create 3 immediate tasks
         * 2. create 2 future tasks each with schedule of 7, and 12 seconds resp
         * 3. intermittently keep checking the dashboard tasks api for
         *      taskcount: int
         *      byStatus :{
         *          created: [] //list of tasks,
         *          completed: [],
         *          failed: [],
         *          executing: [],
         *          flagged: []
         *      }
         */

        //setup:
        //creating  tasks
        //immediate tasks
        for (int i = 0; i < 3; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }
        //future task1
        CreateTaskDTO futureTaskPayload = MockData.getCreateTaskDTO(); //a tasks scheduled for ten seconds later
        futureTaskPayload.setScheduleMeta(new ScheduleMetaDTO(ScheduleType.FUTURE, String.valueOf(7)));
        Response response = RestWrapper
                .given()
                .body(futureTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(201, response.getStatusCode());
        //future task2
        futureTaskPayload.setScheduleMeta(new ScheduleMetaDTO(ScheduleType.FUTURE, String.valueOf(12)));
        response = RestWrapper
                .given()
                .body(futureTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(201, response.getStatusCode());


        //execution:
        //preparing and starting the scheduler for 1 thread poolsize & with polldelay 5 secs
        scheduler.identity("test-scheduler");
        scheduler.configure(1, TimeUnit.SECONDS.toMillis(5), dbClient, cacheClient);
        scheduler.start();

        //waiting 5 seconds and checking
        // if total tasks is 5
        // completed is 3
        // created is 2
        sleep(TimeUnit.SECONDS.toMillis(5));
        Response dashboardResponse = RestWrapper
                .given()
                .get(ApiConstants.Tasks.GET_DASHBOARD);
        LOG.info("get  task dashboard response {}", dashboardResponse.asString());
        Assert.assertEquals(200, dashboardResponse.getStatusCode());
        TaskDashboardDTO result = RestParser.getTaskDashboardDTO(dashboardResponse);
        Assert.assertEquals(5, result.getCount());
        Assert.assertEquals(3, result.getByStatus().get(TaskStatus.COMPLETED).size());
        Assert.assertEquals(2, result.getByStatus().get(TaskStatus.CREATED).size());

        //waiting 5 seconds and checking
        // if total tasks is 5
        // completed is 3
        // created is 2
        sleep(TimeUnit.SECONDS.toMillis(5));
        dashboardResponse = RestWrapper
                .given()
                .get(ApiConstants.Tasks.GET_DASHBOARD);
        LOG.info("get  task dashboard response {}", dashboardResponse.asString());
        Assert.assertEquals(200, dashboardResponse.getStatusCode());
        result = RestParser.getTaskDashboardDTO(dashboardResponse);
        Assert.assertEquals(5, result.getCount());
        Assert.assertEquals(4, result.getByStatus().get(TaskStatus.COMPLETED).size());
        Assert.assertEquals(1, result.getByStatus().get(TaskStatus.CREATED).size());

        //waiting 5 seconds and checking
        // if total tasks is 5
        // completed is 5
        // created is 0
        sleep(TimeUnit.SECONDS.toMillis(5));
        dashboardResponse = RestWrapper
                .given()
                .get(ApiConstants.Tasks.GET_DASHBOARD);
        LOG.info("get  task dashboard response {}", dashboardResponse.asString());
        Assert.assertEquals(200, dashboardResponse.getStatusCode());
        result = RestParser.getTaskDashboardDTO(dashboardResponse);
        Assert.assertEquals(5, result.getCount());
        Assert.assertEquals(5, result.getByStatus().get(TaskStatus.COMPLETED).size());
        Assert.assertEquals(0, result.getByStatus().get(TaskStatus.CREATED).size());

        List<TaskDTO> pendingTasks = dbClient.getPendingTasks();
        Assert.assertTrue(pendingTasks.isEmpty());
        Assert.assertTrue(cacheClient.count() == 0);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

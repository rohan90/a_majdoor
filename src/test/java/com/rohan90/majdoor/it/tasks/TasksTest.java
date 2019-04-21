package com.rohan90.majdoor.it.tasks;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.tasks.domain.dtos.*;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.api.utils.rest.RestParser;
import com.rohan90.majdoor.api.utils.rest.RestWrapper;
import com.rohan90.majdoor.executor.operators.OperatorType;
import com.rohan90.majdoor.utils.BaseAPITest;
import com.rohan90.majdoor.utils.MockData;
import com.rohan90.majdoor.utils.validators.TaskAssertions;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TasksTest extends BaseAPITest {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());


    @Test
    public void shouldBeAbleToCreateATask() {
        CreateTaskDTO createTaskPayload = MockData.getCreateTaskDTO();
        Response response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        LOG.info("creating task response {}", response.asString());

        Assert.assertEquals(201, response.getStatusCode());
        TaskDTO createdTask = RestParser.getTaskDTO(response);
        TaskAssertions.assertTaskCreated(createTaskPayload, createdTask);
    }

    @Test
    public void shouldNotBeAbleToCreateATask_If_IncorrectPayload() {
        CreateTaskDTO createTaskPayload = new CreateTaskDTO();

        Response response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(400, response.getStatusCode());

        createTaskPayload.setName("some name");
        response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(400, response.getStatusCode());

        createTaskPayload.setDescription("some descroption");
        response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(400, response.getStatusCode());

        createTaskPayload.setCreatedBy(new UserDTO("some user", 1));
        response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(400, response.getStatusCode());

        createTaskPayload.setOperator(new TaskOperatorDTO(OperatorType.PRINT, "hello"));
        response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(400, response.getStatusCode());

        createTaskPayload.setScheduleMeta(new ScheduleMetaDTO(ScheduleType.IMMEDIATE, "0"));
        response = RestWrapper
                .given()
                .body(createTaskPayload)
                .post(ApiConstants.Tasks.CREATE);
        Assert.assertEquals(201, response.getStatusCode());

        TaskDTO createdTask = RestParser.getTaskDTO(response);
        TaskAssertions.assertTaskCreated(createTaskPayload, createdTask);

    }

    @Test
    public void shouldBeAbleToGetAllTasksInTheSystem() {
        //for now a get all tasks
        //todo maybe a filter for tasks by status, type etc

        int TASKS_TO_CREATE_COUNT = 10;
        for (int i = 0; i < 10; i++) {
            Response response = RestWrapper
                    .given()
                    .body(MockData.getCreateTaskDTO())
                    .post(ApiConstants.Tasks.CREATE);
            Assert.assertEquals(201, response.getStatusCode());
        }

        Response response = RestWrapper
                .given()
                .get(ApiConstants.Tasks.GET_ALL);
        LOG.info("get all task response {}", response.asString());

        Assert.assertEquals(200, response.getStatusCode());
        List<TaskDTO> tasks = RestParser.getTaskDTOs(response);
        Assert.assertEquals(TASKS_TO_CREATE_COUNT, tasks.size());
    }
}

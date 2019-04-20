package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.common.RestResponse;
import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.ENTRY_POINT + ApiConstants.Tasks.BASE)
public class TaskController {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ITaskService service;

    /**
     * Creates a task and saves it in specified repo implementation
     *
     * @param payload {@link CreateTaskDTO}
     * @return task {@link TaskDTO}
     */
    @PostMapping(ApiConstants.Tasks.INDEX)
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<TaskDTO> createTask(@RequestBody @Valid CreateTaskDTO payload) {
        LOG.info("Received task creation request {}", payload);
        TaskDTO createdFacility = service.create(payload);
        return RestResponse.ok(createdFacility);
    }

    /**
     * Get all tasks in the system
     *
     * @return List<Task> {@link TaskDTO}
     */
    @GetMapping(ApiConstants.Tasks.INDEX)
    public RestResponse<List<TaskDTO>> get() {
        LOG.info("Received get tasks request");
        List<TaskDTO> tasks = service.get();
        return RestResponse.ok(tasks);
    }
}

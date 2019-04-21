package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDashboardDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

import java.util.List;

public interface ITaskService {
    TaskDTO create(CreateTaskDTO payload);

    List<TaskDTO> get();

    TaskDTO getById(Long id);

    TaskDTO update(TaskStatus status);

    TaskDashboardDTO getDashboard();

    //todo maybe expose a delete api too?
}

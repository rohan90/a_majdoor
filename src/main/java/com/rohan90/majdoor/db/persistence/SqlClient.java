package com.rohan90.majdoor.db.persistence;

import com.rohan90.majdoor.api.tasks.ITaskRepository;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SqlClient implements IDBClient {

    @Autowired
    ITaskRepository repository;

    @Override
    public List<TaskDTO> getPendingTasks() {
        List<TaskStatus> taskStatuses = Arrays.asList(TaskStatus.CREATED);
        return TaskDTO.transformToDTOs(repository.findByStatusIn(taskStatuses));
    }
}

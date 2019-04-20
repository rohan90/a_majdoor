package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskServiceImpl implements ITaskService {

    @Autowired
    ITaskRepository repository;

    @Override
    public TaskDTO create(CreateTaskDTO payload) {
        Task entity = TaskDTO.transformToEntity(payload);
        entity.setStatus(TaskStatus.CREATED);
        Task createdTask = repository.save(entity);
        return TaskDTO.transformToDTO(createdTask);
    }

    @Override
    public List<TaskDTO> get() {
        return TaskDTO.transformToDTOs(repository.findAll());
    }

    @Override
    public TaskDTO getById(Long id) {
        return null;
    }

    @Override
    public TaskDTO update(TaskStatus status) {
        return null;
    }
}

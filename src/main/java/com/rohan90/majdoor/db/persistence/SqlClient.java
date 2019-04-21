package com.rohan90.majdoor.db.persistence;

import com.rohan90.majdoor.api.tasks.ITaskRepository;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SqlClient implements IDbClient {

    @Autowired
    ITaskRepository repository;

    @Override
    public List<TaskDTO> getPendingTasks() {
        List<TaskStatus> taskStatuses = Arrays.asList(TaskStatus.CREATED);
        return TaskDTO.transformToDTOs(repository.findByStatusIn(taskStatuses));
    }

    @Override
    public void updateTaskStatus(TaskStatus status, Long taskId) {
        Optional<Task> byId = repository.findById(taskId);

        if (!byId.isPresent()) {
            throw new RuntimeException("Task with id ," + taskId + " not found");
        }
        Task task = byId.get();
        task.setStatus(status);
        repository.save(task);
    }
}

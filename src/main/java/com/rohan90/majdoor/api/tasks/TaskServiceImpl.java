package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDashboardDTO;
import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public TaskDashboardDTO getDashboard() {
        List<Task> all = repository.findAll();

        TaskDashboardDTO dto = new TaskDashboardDTO();
        dto.setCount(all.size());

        List<TaskDTO> created = all
                .stream()
                .filter(task -> TaskStatus.CREATED.equals(task.getStatus()))
                .map(task -> TaskDTO.transformToDTO(task))
                .collect(Collectors.toList());

        List<TaskDTO> completed = all
                .stream()
                .filter(task -> TaskStatus.COMPLETED.equals(task.getStatus()))
                .map(task -> TaskDTO.transformToDTO(task))
                .collect(Collectors.toList());
        List<TaskDTO> failed = all
                .stream()
                .filter(task -> TaskStatus.FAILED.equals(task.getStatus()))
                .map(task -> TaskDTO.transformToDTO(task))
                .collect(Collectors.toList());

        HashMap<TaskStatus, List<TaskDTO>> byStatus = new HashMap<>();
        byStatus.put(TaskStatus.CREATED, created);
        byStatus.put(TaskStatus.COMPLETED, completed);
        byStatus.put(TaskStatus.FAILED, failed);

        dto.setByStatus(byStatus);

        //todo do something about other statuses
        // for now leaving it at created and completed.
        return dto;
    }
}

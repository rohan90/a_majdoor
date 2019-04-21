package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatusIn(List<TaskStatus> taskStatuses);
}

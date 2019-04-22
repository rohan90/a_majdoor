package com.rohan90.majdoor.db.persistence;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

import java.util.List;

/**
 * The contract to access and modify tasks from db/datacenters.
 */
public interface IDbClient {
    /**
     * All tasks which are either in Status Created
     *
     * @return
     */
    List<TaskDTO> getPendingTasks();

    /**
     * used for updating the status of a task by taskId
     *
     * @param status
     * @param taskId
     */
    void updateTaskStatus(TaskStatus status, Long taskId);
}

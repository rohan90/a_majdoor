package com.rohan90.majdoor.db.persistence;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;

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
}

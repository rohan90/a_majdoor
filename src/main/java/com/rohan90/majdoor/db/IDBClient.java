package com.rohan90.majdoor.db;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;

import java.util.List;

/**
 * The contract to access and modify tasks from db/datacenters.
 */
public interface IDBClient {
    List<TaskDTO> getTasks();
}

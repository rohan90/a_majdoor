package com.rohan90.majdoor.api.tasks.domain.dtos;

import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

import java.util.List;
import java.util.Map;

public class TaskDashboardDTO {
    private int count;
    private Map<TaskStatus, List<TaskDTO>> byStatus;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<TaskStatus, List<TaskDTO>> getByStatus() {
        return byStatus;
    }

    public void setByStatus(Map<TaskStatus, List<TaskDTO>> byStatus) {
        this.byStatus = byStatus;
    }
}

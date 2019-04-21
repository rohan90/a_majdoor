package com.rohan90.majdoor.api.tasks.domain.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
    private long id; //todo maybe should be uuid?
    private String name;
    private String description;
    private ScheduleMetaDTO scheduleMeta;
    private TaskOperatorDTO operator;
    private UserDTO createdBy;
    private TaskStatus status;
    private long lastExecuted;
    private String executedByNodeId; //todo might be usefull later.

    public static Task transformToEntity(CreateTaskDTO payload) {
        //todo possibly have a builder pattern here?
        Task entity = new Task();
        entity.setName(payload.getName());
        entity.setDescription(payload.getDescription());
        entity.setCreatedBy(UserDTO.transformToEntity(payload.getCreatedBy()));
        try {
            entity.setOperator(new ObjectMapper().writeValueAsString(payload.getOperator()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("some thing happened while parsing operator for storage");
        }
        entity.setSchedule(ScheduleMetaDTO.transformToEntity(payload.getScheduleMeta()));

        return entity;
    }

    public static TaskDTO transformToDTO(Task entity) {
        //todo possibly have a builder pattern here?
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setExecutedByNodeId(entity.getExecutedByNodeId());
        dto.setLastExecuted(entity.getLastExecuted());
        try {
            dto.setOperator(new ObjectMapper().readValue(entity.getOperator(), TaskOperatorDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("some thing happened while parsing operator for rest-response");
        }
        dto.setScheduleMeta(ScheduleMetaDTO.transformToDTO(entity.getSchedule()));
        dto.setCreatedBy(UserDTO.transformToDTO(entity.getCreatedBy()));
        return dto;
    }

    public static List<TaskDTO> transformToDTOs(List<Task> entitites) {
        List<TaskDTO> dtos = new ArrayList<>();
        entitites.forEach(e -> {
            dtos.add(TaskDTO.transformToDTO(e));
        });
        return dtos;
    }

    /**
     * returns millis of value submitted
     * <p>
     * todo should possibly also be a epoch/cron parser?
     *
     * @param value
     * @return
     */
    public static long getDelayInMillis(String value) {
        return Long.valueOf(value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScheduleMetaDTO getScheduleMeta() {
        return scheduleMeta;
    }

    public void setScheduleMeta(ScheduleMetaDTO scheduleMeta) {
        this.scheduleMeta = scheduleMeta;
    }

    public TaskOperatorDTO getOperator() {
        return operator;
    }

    public void setOperator(TaskOperatorDTO operator) {
        this.operator = operator;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getLastExecuted() {
        return lastExecuted;
    }

    public void setLastExecuted(long lastExecuted) {
        this.lastExecuted = lastExecuted;
    }

    public String getExecutedByNodeId() {
        return executedByNodeId;
    }

    public void setExecutedByNodeId(String executedByNodeId) {
        this.executedByNodeId = executedByNodeId;
    }
}

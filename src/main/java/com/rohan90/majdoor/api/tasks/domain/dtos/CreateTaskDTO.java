package com.rohan90.majdoor.api.tasks.domain.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateTaskDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Valid
    @NotNull
    private ScheduleMetaDTO scheduleMeta;
    @Valid
    @NotNull
    private TaskOperatorDTO operator;
    @Valid
    @NotNull
    private UserDTO createdBy; //can be used later for authentication/authorization

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

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public TaskOperatorDTO getOperator() {
        return operator;
    }

    public void setOperator(TaskOperatorDTO operator) {
        this.operator = operator;
    }
}

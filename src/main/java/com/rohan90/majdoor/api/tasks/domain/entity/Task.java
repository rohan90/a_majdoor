package com.rohan90.majdoor.api.tasks.domain.entity;

import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    @Size(max = 100)
    private String name;

    @Column(nullable = false)
    @Size(max = 500)
    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "id", column = @Column(name = "created_by_user_id")),
            @AttributeOverride(name = "name", column = @Column(name = "created_by_user_name"))
    })
    private User createdBy;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private long lastExecuted;

    @Column()
    @Size(max = 100)
    private String executedByNodeId;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "type", column = @Column(name = "schedule_type")),
            @AttributeOverride(name = "value", column = @Column(name = "schedule_value"))
    })
    private ScheduleMeta schedule;

    @Column(nullable = false)
    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
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

    public ScheduleMeta getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleMeta schedule) {
        this.schedule = schedule;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

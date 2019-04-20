package com.rohan90.majdoor.api.tasks.domain.dtos;

import com.rohan90.majdoor.api.tasks.domain.entity.ScheduleMeta;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ScheduleMetaDTO {
    @NotNull
    private ScheduleType type;
    @NotEmpty
    private String value; //todo: expand this to a cronExpression and possible cron validations and parsing for schedule implementations.

    public ScheduleMetaDTO(ScheduleType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static ScheduleMeta transformToEntity(ScheduleMetaDTO dto) {
        ScheduleMeta entity = new ScheduleMeta();
        entity.setType(dto.getType());
        entity.setValue(dto.getValue());
        return entity;
    }

    public static ScheduleMetaDTO transformToDTO(ScheduleMeta entity) {
        ScheduleMetaDTO dto = new ScheduleMetaDTO(entity.getType(),entity.getValue());
        return dto;
    }

    public ScheduleType getType() {
        return type;
    }

    public void setType(ScheduleType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

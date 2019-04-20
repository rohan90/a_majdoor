package com.rohan90.majdoor.api.tasks.domain.entity;

import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ScheduleMeta {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    private String value;

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

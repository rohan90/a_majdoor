package com.rohan90.majdoor.api.tasks.domain.dtos;

import com.rohan90.majdoor.executor.operators.OperatorType;

import javax.validation.constraints.NotNull;

//todo create custom validators so as to not have incorrect payload in operator. For now left a null check
public class TaskOperatorDTO<T> {
    @NotNull
    private OperatorType type;
    @NotNull
    private T value;

    public TaskOperatorDTO() {
    }

    public TaskOperatorDTO(OperatorType type, T value) {
        this.type = type;
        this.value = value;
    }

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

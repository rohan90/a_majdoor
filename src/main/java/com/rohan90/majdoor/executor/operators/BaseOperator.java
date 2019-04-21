package com.rohan90.majdoor.executor.operators;

public abstract class BaseOperator<T> implements Operator {
    protected T data;
    protected abstract OperatorType type();
}

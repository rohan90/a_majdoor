package com.rohan90.majdoor.executor.operators;

public abstract class BaseOperator<T> {
    T data;
    abstract void execute();
    abstract OperatorType type();
}

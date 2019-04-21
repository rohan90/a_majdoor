package com.rohan90.majdoor.executor.operators;

public abstract class BaseOperator<T> implements Operator {
    T data;
    abstract OperatorType type();
}

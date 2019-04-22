package com.rohan90.majdoor.executor.operators;

/**
 * Blueprint of an Operator, used by the XTaskRunners
 * @param <T>
 */
public abstract class BaseOperator<T> implements Operator {
    protected T data;
    protected abstract OperatorType type();
}

package com.rohan90.majdoor.executor.operators;

public class PrintOperator<String> extends BaseOperator<String> {
    @Override
    void execute() {
        System.out.println(data);
    }

    @Override
    OperatorType type() {
        return OperatorType.PRINT;
    }
}

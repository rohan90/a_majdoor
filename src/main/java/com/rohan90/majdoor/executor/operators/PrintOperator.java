package com.rohan90.majdoor.executor.operators;

public class PrintOperator<String> extends BaseOperator<String> {
    public PrintOperator(String value) {
        this.data = value;
    }

    @Override
    public void execute() {
        System.out.println("----START----\n");
        System.out.println(data);
        System.out.println("\n----DONE-----\n");
    }

    @Override
    OperatorType type() {
        return OperatorType.PRINT;
    }
}

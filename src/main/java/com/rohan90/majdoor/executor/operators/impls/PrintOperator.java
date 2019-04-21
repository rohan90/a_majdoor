package com.rohan90.majdoor.executor.operators.impls;

import com.rohan90.majdoor.executor.operators.BaseOperator;
import com.rohan90.majdoor.executor.operators.OperatorType;

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
    protected OperatorType type() {
        return OperatorType.PRINT;
    }
}

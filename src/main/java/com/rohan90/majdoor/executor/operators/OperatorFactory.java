package com.rohan90.majdoor.executor.operators;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskOperatorDTO;
import com.rohan90.majdoor.executor.operators.impls.PrintOperator;
import com.rohan90.majdoor.executor.operators.impls.SmsOperator;

public class OperatorFactory {
    public static Operator get(TaskOperatorDTO operator) {
        Operator value = null;
        switch (operator.getType()) {
            case PRINT:
                value = new PrintOperator<>(operator.getValue());
                break;
            case SMS:
                value = new SmsOperator<>(operator.getValue());
                break;
            default:
                throw new RuntimeException("unknown operator encountered");
        }
        return value;
    }
}

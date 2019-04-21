package com.rohan90.majdoor.executor.operators.impls;

import com.rohan90.majdoor.executor.operators.BaseOperator;
import com.rohan90.majdoor.executor.operators.OperatorType;

public class SmsOperator<SmsOperatorPayload> extends BaseOperator<SmsOperatorPayload> {
    public SmsOperator(SmsOperatorPayload value) {
        this.data = value;
    }

    @Override
    public void execute() {
        System.out.println("----START----\n");
        // for the scope of the interview this task will always fail
        throw new RuntimeException("Have not implemented SMS client/ sms API");
    }

    @Override
    protected OperatorType type() {
        return OperatorType.SMS;
    }
}

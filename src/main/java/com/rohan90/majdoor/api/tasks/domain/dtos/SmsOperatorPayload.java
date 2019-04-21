package com.rohan90.majdoor.api.tasks.domain.dtos;

public class SmsOperatorPayload {
    private String phoneNumber;
    private String message;

    public SmsOperatorPayload() {
    }

    public SmsOperatorPayload(String message, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }
}

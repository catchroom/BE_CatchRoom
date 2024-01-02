package com.example.catchroom_be.global.exception;

public class SuccessMessage extends ResponseMessage{
    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }

    public static SuccessMessage createSuccessMessage(String message) {
        return new SuccessMessage(message);
    }

}

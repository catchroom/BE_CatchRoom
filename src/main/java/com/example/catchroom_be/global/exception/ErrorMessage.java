package com.example.catchroom_be.global.exception;

public class ErrorMessage extends ResponseMessage{

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public static ErrorMessage createErrorMessage(String message) {
        return new ErrorMessage(message);
    }
}

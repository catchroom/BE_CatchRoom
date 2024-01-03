package com.example.catchroom_be.global.exception;

public record ErrorMessage(
        String message
) {
    public static ErrorMessage createErrorMessage(String message) {
        return new ErrorMessage(message);
    }
}

package com.example.catchroom_be.global.exception;

public record SuccessMessage(
        String meassage
) {
    public static SuccessMessage createSuccessMessage(String message) {
        return new SuccessMessage(message);
    }
}

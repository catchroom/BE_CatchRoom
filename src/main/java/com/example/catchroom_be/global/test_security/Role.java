package com.example.catchroom_be.global.test_security;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Role {
    MEMBER, ADMIN;

    @JsonCreator
    public static Role parse(String inputValue) {
        return Stream.of(Role.values())
            .filter(role -> role.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}

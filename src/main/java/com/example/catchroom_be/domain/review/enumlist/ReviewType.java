package com.example.catchroom_be.domain.review.enumlist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ReviewType {
    BUY("구매"),
    SELL("판매");

    private String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static ReviewType fromString(String value) {
        return Arrays.stream(ReviewType.values())
                .filter(type -> type.type.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ReviewType: " + value));
    }
}

package com.example.catchroom_be.domain.product.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportationType {

    WALK("도보"),
    CAR("차량")
    ;

    private String value;
}

package com.example.catchroom_be.domain.user.dto.response;


import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class DepositResponse {
    private LocalDateTime date;
    private String type;
    private int money;
    private String info;
    private Long id;

    public DepositResponse(LocalDateTime createdAt, String type, int money, String info, Long id) {
        this.date = createdAt;
        this.type = type;
        this.money = money;
        this.info = info;
        this.id = id;

    }
}

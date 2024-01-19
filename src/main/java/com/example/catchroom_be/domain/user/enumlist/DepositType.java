package com.example.catchroom_be.domain.user.enumlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum DepositType {
    WITHDRAW("출금"),
    DEPOSIT("적립");

    private String type;

}

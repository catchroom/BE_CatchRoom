package com.example.catchroom_be.domain.user.dto.response;

import com.example.catchroom_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositAccountNumResponse {
    String accountNumber;
    Integer balance;
    String accountOwner;
    String bankName;



    public static DepositAccountNumResponse fromEntity(User user) {

        return DepositAccountNumResponse.builder()
                .accountNumber(user.getAccountNumber())
                .balance(user.getBalance())
                .accountOwner(user.getAccountOwner())
                .bankName(user.getBankName())
                .build();
    }
}

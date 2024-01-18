package com.example.catchroom_be.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountNumRequest {
    String bankName;
    String accountNumber;
    String accountOwner;
}

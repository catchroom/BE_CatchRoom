package com.example.catchroom_be.domain.test_user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLogInRequest {

    private String email;

    private String password;
}

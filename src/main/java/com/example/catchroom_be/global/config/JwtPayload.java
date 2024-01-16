package com.example.catchroom_be.global.config;

import lombok.Builder;

import java.util.Date;

@Builder
public record JwtPayload(String email, String nickName, String phoneNumber, String name, Date issuedAt) {

}

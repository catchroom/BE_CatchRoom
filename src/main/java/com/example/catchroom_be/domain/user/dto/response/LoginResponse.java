package com.example.catchroom_be.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder

public class LoginResponse {


    String accessToken;
    String refreshToken;
    Long id;


    public static LoginResponse fromEntity(Long id,String accessToken,String refreshToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(id)
                .build();
    }
}

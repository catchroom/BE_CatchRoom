package com.example.catchroom_be.domain.user.dto.response;

import com.example.catchroom_be.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    String email;
    String name;
    String phoneNumber;
    String nickName;
    Long userId;


    public static ProfileResponse fromEntity(User user) {
        return ProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhonenumber())
                .nickName(user.getNickName())
                .userId(user.getId())
                .build();
    }
}

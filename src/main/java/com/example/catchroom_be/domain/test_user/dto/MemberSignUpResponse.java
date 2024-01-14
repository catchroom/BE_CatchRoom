package com.example.catchroom_be.domain.test_user.dto;

import com.example.catchroom_be.domain.test_user.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpResponse {
    private Long id;
    private String email;
    private String name;

    @Builder
    public MemberSignUpResponse(Long id,String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static MemberSignUpResponse fromEntity(Member member) {
        return MemberSignUpResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .build();

    }
}

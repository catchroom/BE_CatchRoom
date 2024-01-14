package com.example.catchroom_be.domain.test_user.service;

import com.example.catchroom_be.domain.test_user.dto.*;
import com.example.catchroom_be.domain.test_user.entity.Member;
import com.example.catchroom_be.domain.test_user.repository.MemberRepository;
import com.example.catchroom_be.global.test_security.JwtService;
import com.example.catchroom_be.global.test_security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    @Transactional
    public MemberSignUpResponse signup(MemberSignUpRequest memberSignUpRequest) {
        validateEmailDuplicated(memberSignUpRequest);
        Member member = memberSignUpRequest.toEntity(memberSignUpRequest);
        member.encodePassword(passwordEncoder);

        return MemberSignUpResponse.fromEntity(memberRepository.save(member));
    }

    @Transactional
    public MemberLogInResponse login(MemberLogInRequest memberLogInRequest) {
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(memberLogInRequest.getEmail(), memberLogInRequest.getPassword())
        );
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        Member member = memberRepository.getReferenceById(memberDetails.getId());
        String accessToken = jwtService.issue(member.getId(), member.getEmail(), member.getRole());
        return MemberLogInResponse.fromEntity(member,accessToken);
    }


    private void validateEmailDuplicated(MemberSignUpRequest memberSignUpRequest) {
        memberRepository.findByEmail(memberSignUpRequest.getEmail()).ifPresent(member -> {
                throw new IllegalArgumentException();
            }
        );
    }
}

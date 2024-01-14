package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeLoginService {

    private final UserEntityRepository userEntityRepository;
    private final MeJWTService meJWTService;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse loginUser(LoginRequest loginRequest) {
        String rawPassword = loginRequest.getPassword();
        String email = loginRequest.getEmail();

        User user = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.USER_CANNOT_LOGIN));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserException(ErrorCode.USER_CANNOT_LOGIN);
        }

        return LoginResponse.builder()
                .accessToken(meJWTService.createAccessToken())
                .refreshToken(meJWTService.createRefreshToken())
                .id(user.getId())
                .build();
    }


}

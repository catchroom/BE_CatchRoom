package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.config.JwtPayload;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeLoginService {

    private final UserEntityRepository userEntityRepository;
    private final MeJWTService meJWTService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;



    public LoginResponse loginUser(LoginRequest loginRequest) {
        String rawPassword = loginRequest.getPassword();
        String email = loginRequest.getEmail();

        User user = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.USER_CANNOT_LOGIN));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserException(ErrorCode.USER_CANNOT_LOGIN);
        }

        JwtPayload jwtPayload = JwtPayload.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .name(user.getName())
                .phoneNumber(user.getPhonenumber())
                .issuedAt(new Date())
                .build();

        String refreshToken = meJWTService.createRefreshToken(jwtPayload);

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(String.valueOf(user.getId()), refreshToken,3000 * 60 * 1000L);



        return LoginResponse.builder()
                .accessToken(meJWTService.createAccessToken(jwtPayload))
                .refreshToken(refreshToken)
                .id(user.getId())
                .build();
    }


}

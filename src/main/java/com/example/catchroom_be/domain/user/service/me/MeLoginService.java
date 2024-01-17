package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.jwt.dto.JwtPayload;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.jwt.service.MeJWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MeLoginService {

    private final UserEntityRepository userEntityRepository;
    private final MeJWTService meJWTService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final int idCookieValidTime = 60 * 1000;

    private final int accessTokenCookieValidTime = /*30  **/ 60 * 1000; // access토큰의 유효시간 (30분)

    private final int refreshTokenCookieValidTime = 3000 * 60 * 1000; //refresh 토큰의 유효시간 (3000분)
    private final int refreshTokenRedisValidTime = 3000 * 60 * 1000;


    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest, HttpServletResponse response) {
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

        /*String id = String.valueOf(user.getId());
        String accessToken = meJWTService.createAccessToken(jwtPayload);*/
        String refreshToken = meJWTService.createRefreshToken(jwtPayload);


        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(String.valueOf(user.getId()),refreshToken,refreshTokenRedisValidTime, TimeUnit.MILLISECONDS);

         /*Cookie idCookie = new Cookie("id", id);
        idCookie.setHttpOnly(true);
        idCookie.setMaxAge(idCookieValidTime); // 30분
        idCookie.setPath("/");
        response.addCookie(idCookie);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(accessTokenCookieValidTime); // 30분
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        // 응답에 리프레시 토큰 쿠키 추가
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(refreshTokenCookieValidTime); // 3000분
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);*/


        return LoginResponse.builder()
                .accessToken(meJWTService.createAccessToken(jwtPayload))
                .refreshToken(refreshToken)
                .id(user.getId())
                .build();



    }

}

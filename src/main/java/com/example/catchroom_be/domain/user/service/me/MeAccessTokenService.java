package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.jwt.dto.JwtPayload;
import com.example.catchroom_be.global.jwt.service.MeJWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeAccessTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MeJWTService meJWTService;


    public String accessTokenService(HttpServletRequest request, @AuthenticationPrincipal User user, HttpServletResponse response) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);


        String refreshToken = bearerToken.substring(7);


        //redis에 있는 지 확인
        Object checkToken = redisTemplate.opsForValue().get(String.valueOf(user.getId()));


        if (checkToken == null || !checkToken.toString().equals(refreshToken)) {

            throw new UserException(ErrorCode.USER_REFRESH_TOKEN_NOT_IN_REDIS);
        }


        try {
            // Refresh Token을 검증
            JwtPayload jwtPayload = meJWTService.verifyToken(refreshToken);
            // 새로운 Access Token을 생성
            String newAccessToken = meJWTService.createAccessToken(jwtPayload);


            return newAccessToken;


        }  catch (CustomAuthenticationException e) {
            throw new UserException(ErrorCode.SERVER_ERROR);
        }
    }
}

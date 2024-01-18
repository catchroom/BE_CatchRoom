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
    private final int accessTokenCookieValidTime = 30 * 60 * 1000; // access토큰의 유효시간 (30분)

    public String accessTokenService(HttpServletRequest request, @AuthenticationPrincipal User user, HttpServletResponse response) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);


        //header에 refresh token이 있는지 확인
        if (bearerToken == null || !bearerToken.startsWith("Bearer")) {
            throw new UserException(ErrorCode.USER_REFRESHTOKEN_MISSING);
        }


        String refreshToken = bearerToken.substring(7);

        System.out.println(refreshToken);
        //redis에 있는 지 확인
        Object checkToken = redisTemplate.opsForValue().get(String.valueOf(user.getId()));


        if (checkToken == null || !checkToken.toString().equals(refreshToken)) {

            throw new UserException(ErrorCode.USER_REFRESHTOKEN_MISSING);
        }


        try {
            // Refresh Token을 검증
            JwtPayload jwtPayload = meJWTService.verifyToken(refreshToken);

            // 새로운 Access Token을 생성
            String newAccessToken = meJWTService.createAccessToken(jwtPayload);

           /* Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setMaxAge(accessTokenCookieValidTime); // 30분
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);*/

            return newAccessToken;


        }  catch (CustomAuthenticationException e) {
            throw new UserException(ErrorCode.SERVER_ERROR);
        }
    }
}

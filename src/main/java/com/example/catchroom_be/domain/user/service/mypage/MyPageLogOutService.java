package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageLogOutService {

    private final RedisTemplate<String, Object> redisTemplate;
    public void logoutService(HttpServletRequest request, @AuthenticationPrincipal User user) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        //header에 refresh token이 있는지 확인
        if (bearerToken == null || !bearerToken.startsWith("Bearer")) {
            throw new UserException(ErrorCode.MYPAGE_LOGOUT_ERROR);
        }

        String refreshToken = bearerToken.substring(7);

        //redis에 있는 지 확인
        Boolean tokenExists = redisTemplate.hasKey(String.valueOf(user.getId()));

        if (tokenExists == null || !tokenExists) {
            throw new UserException(ErrorCode.MYPAGE_LOGOUT_ERROR);
        } else {
            redisTemplate.delete(String.valueOf(user.getId()));
        }

    }
}

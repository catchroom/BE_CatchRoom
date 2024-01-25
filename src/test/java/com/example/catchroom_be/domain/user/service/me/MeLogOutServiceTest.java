package com.example.catchroom_be.domain.user.service.me;


import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.service.mypage.MyPageLogOutService;
import com.example.catchroom_be.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeLogOutServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HttpServletRequest request;

    @Mock
    private User user;

    @InjectMocks
    private MyPageLogOutService myPageLogOutService;

    @Mock
    private ValueOperations<String, Object> valueOperations;


    @BeforeEach
    public void setUp() {
        when(user.getId()).thenReturn(1L);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("로그아웃 됐을때 케이스")
    public void logout_thenNoException() {
        String bearerToken = "Bearer token";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        when(redisTemplate.opsForValue().get(String.valueOf(user.getId()))).thenReturn("refresh-token");

        myPageLogOutService.logoutService(request, user);

        verify(redisTemplate).delete(String.valueOf(user.getId()));
    }


    @Test
    @DisplayName("로그아웃 안됐을 때 케이스")
    public void logout_thenException() {
        String bearerToken = "Bearer token";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        when(redisTemplate.opsForValue().get(String.valueOf(user.getId()))).thenReturn(null);

        Exception exception = assertThrows(UserException.class, () -> myPageLogOutService.logoutService(request, user));
        assertTrue(exception.getMessage().contains(ErrorCode.MYPAGE_LOGOUT_ERROR.getMessage()));
    }
}


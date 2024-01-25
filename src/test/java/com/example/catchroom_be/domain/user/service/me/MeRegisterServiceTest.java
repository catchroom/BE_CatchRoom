package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.orderhistory.service.OrderHistoryService;
import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MeRegisterServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderHistoryService orderHistoryService;

    @InjectMocks
    private MeRegisterService meRegisterService;

    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        registerRequest = RegisterRequest.builder()
                .email("pgw111111@naver.com")
                .password("password")
                .nickname("nickname")
                .phonenumber("01012345678")
                .name("name")
                .build();
    }

    @Test
    @DisplayName("이메일이 중복되지 않았을 때 케이스")
    public void whenEmailIsNotDuplicated_thenNoException() {
        User user = User.builder()
                .name(registerRequest.getName())
                .nickName(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password("encodedPassword")
                .phonenumber(registerRequest.getPhonenumber())
                .build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userEntityRepository.countByEmail(registerRequest.getEmail())).thenReturn(0);
        when(userEntityRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(orderHistoryService).insertDataOrderHistory(any(User.class));

        assertDoesNotThrow(() -> meRegisterService.registerUser(registerRequest));
    }

    @Test
    @DisplayName("이메일이 중복되었을 때 케이스")
    public void whenEmailIsDuplicated_thenThrowException() {
        when(userEntityRepository.countByEmail(registerRequest.getEmail())).thenReturn(1);
        Exception exception = assertThrows(UserException.class, () -> meRegisterService.registerUser(registerRequest));
        assertTrue(exception.getMessage().contains(ErrorCode.USER_EMAIL_DUPLICATE.getMessage()));
    }

    @Test
    @DisplayName("닉네임이 중복되지 않았을 때 케이스")
    public void whenNickNameIsNotDuplicated_thenNoException() {
        User user = User.builder()
                .name(registerRequest.getName())
                .nickName(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password("encodedPassword")
                .phonenumber(registerRequest.getPhonenumber())
                .build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userEntityRepository.countByNickName(registerRequest.getNickname())).thenReturn(0);
        when(userEntityRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(orderHistoryService).insertDataOrderHistory(any(User.class));

        assertDoesNotThrow(() -> meRegisterService.registerUser(registerRequest));
    }

    @Test
    @DisplayName("닉네임이 중복되었을 때 예외를 발생시킨다.")
    public void whenNickNameIsDuplicated_thenThrowException() {
        when(userEntityRepository.countByNickName(registerRequest.getNickname())).thenReturn(1);
        Exception exception = assertThrows(UserException.class, () -> meRegisterService.registerUser(registerRequest));
        assertTrue(exception.getMessage().contains(ErrorCode.USER_NICKNAME_DUPLICATE.getMessage()));
    }
}

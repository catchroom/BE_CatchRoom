package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.account.service.AccountService;
import com.example.catchroom_be.domain.orderhistory.service.OrderHistoryService;
import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeRegisterService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderHistoryService orderHistoryService;
    private final AccountService accountService;

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {

        if (userEntityRepository.countByEmail(registerRequest.getEmail()) > 0) {
            throw new UserException(ErrorCode.USER_EMAIL_NOT_DUPLICATE);
        }
        if (userEntityRepository.countByNickName(registerRequest.getNickname()) > 0) {
            throw new UserException(ErrorCode.USER_NICKNAME_NOT_DUPLICATE);
        }


        String password = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder().name(registerRequest.getName())
                .nickName(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password(password)
                .phonenumber(registerRequest.getPhonenumber())
                .build();

        User saveUser = userEntityRepository.save(user);

        insertAccount(user.getName());
        insertOrderHistoryList(saveUser);

    }


    public void insertOrderHistoryList(User user) {
        /** 회원가입 시 주문 기록 넣어주는 로직 **/
        orderHistoryService.insertDataOrderHistory(user);
    }


    public void insertAccount(String userName) {
        /** 회원가입 시 계좌 만들어주는 로직 **/
        accountService.createAccount(userName);
    }
}

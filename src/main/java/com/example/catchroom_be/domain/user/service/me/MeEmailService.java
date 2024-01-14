package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeEmailService {

    private final UserEntityRepository userEntityRepository;


    public void emailCheckUser(String email) {

        userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.USER_EMAIL_DUPLICATE));

    }
}

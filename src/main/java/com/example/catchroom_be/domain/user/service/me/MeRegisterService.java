package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
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
public class MeRegisterService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest registerRequest) {

        if (userEntityRepository.countByEmail(registerRequest.getEmail()) > 0) {
            throw new UserException(ErrorCode.USER_EMAIL_DUPLICATE);
        }
        if (userEntityRepository.countByNickName(registerRequest.getNickname()) > 0) {
            throw new UserException(ErrorCode.USER_NICKNAME_DUPLICATE);
        }



        String password = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder().name(registerRequest.getName())
                .nickName(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password(password)
                .phonenumber(registerRequest.getPhonenumber())
                .build();

        userEntityRepository.save(user);
    }
}

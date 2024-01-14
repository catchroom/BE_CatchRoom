package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MeNickNameService {
    private final UserEntityRepository userEntityRepository;


    public void nicknameCheckUser(String nickname) {

        userEntityRepository.findByNickName(nickname)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NICKNAME_DUPLICATE));


    }
}

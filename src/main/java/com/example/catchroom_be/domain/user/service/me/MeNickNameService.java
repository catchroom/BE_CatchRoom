package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MeNickNameService {
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void nicknameCheckUser(String nickname) {

        if (userEntityRepository.countByNickName(nickname) > 0) {
            throw new UserException(ErrorCode.USER_NICKNAME_DUPLICATE);
        }
    }
}

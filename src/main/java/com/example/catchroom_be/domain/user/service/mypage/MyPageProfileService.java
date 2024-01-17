package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageProfileService {

    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void profileRefactService(String nickName, @AuthenticationPrincipal User user) {
           Long id = user.getId();

           Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                   .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PROFILE_REFACT_ERROR)));
           User resultUser = result.get();
           resultUser.setNickName(nickName);

    }
}

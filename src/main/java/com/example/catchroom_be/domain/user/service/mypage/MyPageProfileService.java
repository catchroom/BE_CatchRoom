package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.user.dto.request.AccountNumRequest;
import com.example.catchroom_be.domain.user.dto.response.DepositAccountNumResponse;
/*import com.example.catchroom_be.domain.user.entity.Account;*/
import com.example.catchroom_be.domain.user.dto.response.ProfileResponse;
import com.example.catchroom_be.domain.user.entity.Account;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
/*import com.example.catchroom_be.domain.user.repository.AccountEntityRepository;*/
import com.example.catchroom_be.domain.user.repository.AccountEntityRepository;
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
    public void nicknameRefactService(String nickName, @AuthenticationPrincipal User user) {
           Long id = user.getId();

           Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                   .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_NICKNAME_REFACT_ERROR)));
           User resultUser = result.get();
           resultUser.setNickName(nickName);

    }

    @Transactional
    public ProfileResponse profileFindService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PROFILE_FIND_ERROR)));

        User resultUser = result.get();


        return ProfileResponse.fromEntity(resultUser);

    }


}

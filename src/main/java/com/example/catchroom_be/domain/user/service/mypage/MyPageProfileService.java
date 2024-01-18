package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.user.dto.request.AccountNumRequest;
import com.example.catchroom_be.domain.user.dto.response.DepositAccountNumResponse;
/*import com.example.catchroom_be.domain.user.entity.Account;*/
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
    private final AccountEntityRepository accountEntityRepository;

    @Transactional
    public void profileRefactService(String nickName, @AuthenticationPrincipal User user) {
           Long id = user.getId();

           Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                   .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PROFILE_REFACT_ERROR)));
           User resultUser = result.get();
           resultUser.setNickName(nickName);

    }

    @Transactional
    public String nickNameFindService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.SERVER_ERROR)));

        User resultUser = result.get();

        return resultUser.getNickName();

    }

    @Transactional
    public DepositAccountNumResponse depositAccountNumFindService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        Optional<User> result = Optional.ofNullable(userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_BALANCE_ERROR)));

        User resultUser = result.get();

        return DepositAccountNumResponse.fromEntity(resultUser);
    }

    @Transactional
    public void accountNumSetService(@AuthenticationPrincipal User user, AccountNumRequest accountNumRequest) {
        Long id = user.getId();

        User resultUser = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.SERVER_ERROR));

        Optional<Account> result = Optional.ofNullable(accountEntityRepository.findByAccountNumberAndAccountOwnerAndBankName(accountNumRequest.getAccountNumber(),
                accountNumRequest.getAccountOwner(),accountNumRequest.getBankName())
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_REGISTER_REFACT_ERROR)));

        Account resultAccount = result.get();

        resultUser.setAccountBankAccountNumAccountOwnerBalance(resultAccount);


    }
}

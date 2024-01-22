package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.user.enumlist.DepositType;
import com.example.catchroom_be.domain.user.dto.request.AccountNumRequest;
import com.example.catchroom_be.domain.user.dto.response.DepositAccountNumResponse;
import com.example.catchroom_be.domain.user.dto.response.DepositResponse;
import com.example.catchroom_be.domain.user.entity.Account;
import com.example.catchroom_be.domain.user.entity.DepositDetails;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.AccountEntityRepository;
import com.example.catchroom_be.domain.user.repository.DepositDetailsRepository;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageAccountService {

    private final UserEntityRepository userEntityRepository;
    private final AccountEntityRepository accountEntityRepository;
    private final DepositDetailsRepository depositDetailsRepository;

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
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_REGISTER_REFACT_ERROR));

        Optional<Account> result = Optional.ofNullable(accountEntityRepository.findByAccountNumberAndAccountOwnerAndBankName(accountNumRequest.getAccountNumber(),
                        accountNumRequest.getAccountOwner(),accountNumRequest.getBankName())
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_REGISTER_REFACT_ERROR)));

        Account resultAccount = result.get();

        resultUser.setBankAccountNumAccountOwnerBalance(resultAccount);


    }

    @Transactional
    public void accountNumPutService(@AuthenticationPrincipal User user,AccountNumRequest accountNumRequest) {
        Long id = user.getId();

        User resultUser = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_REGISTER_REFACT_ERROR));

        Optional<Account> result = Optional.ofNullable(accountEntityRepository.findByAccountNumberAndAccountOwnerAndBankName(accountNumRequest.getAccountNumber(),
                        accountNumRequest.getAccountOwner(),accountNumRequest.getBankName())
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_REGISTER_REFACT_ERROR)));

        Account resultAccount = result.get();

        resultUser.setBankAccountNumAccountOwnerBalance(resultAccount);
    }

    @Transactional
    public void accountNumDeleteService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        User resultUser = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_ACCOUNTNUM_DELETE_ERROR));
        resultUser.deleteBankAccountNumAccountOwnerBalance();

    }

    @Transactional
    public void depositWithdrawService(@AuthenticationPrincipal User user,Long deposit) {
        Long id = user.getId();

        User resultUser = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_DEPOSIT_WITHDRAW_ERROR));
        int tempBalance = resultUser.getBalance();

        if (tempBalance >= deposit) {
            resultUser.minusDepositWithdraw(deposit);

            DepositDetails depositDetail = DepositDetails.builder()
                    .type(DepositType.WITHDRAW.getType()) // 혹은 출금을 나타내는 다른 문자열
                    .money(deposit.intValue()) // 출금 금액
                    .info("예치금") // 혹은 더 상세한 정보
                    .user(resultUser) // 연관된 사용자 엔티티
                    .build();

            depositDetailsRepository.save(depositDetail);
        }
        else {
            throw new UserException(ErrorCode.MYPAGE_DEPOSIT_WITHDRAW_ERROR);
        }
    }

    @Transactional
    public List<DepositResponse> depositListService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        User foundUser = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_DEPOSIT_LIST_ERROR));

        List<DepositDetails> depositDetailsList = foundUser.getDepositDetailsList();

        List<DepositResponse> depositResponseList = depositDetailsList.stream()
                .sorted(Comparator.comparing(DepositDetails::getCreatedAt).reversed())
                .map(depositDetail -> new DepositResponse(
                        depositDetail.getCreatedAt(),
                        depositDetail.getType(),
                        depositDetail.getMoney(),
                        depositDetail.getInfo(),
                        depositDetail.getId()))
                .collect(Collectors.toList());

        for (DepositResponse e : depositResponseList) {
            System.out.println(e.getType());
        }


        return depositResponseList;

    }
}

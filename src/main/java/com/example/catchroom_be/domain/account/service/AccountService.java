package com.example.catchroom_be.domain.account.service;

import com.example.catchroom_be.domain.account.entity.Account;
import com.example.catchroom_be.domain.account.exception.AccountException;
import com.example.catchroom_be.domain.account.repository.AccountRepository;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import jakarta.jws.soap.SOAPBinding.Use;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService  {
    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(String ownerName) {
        List<Account> accountList = new ArrayList<>();

        Random random = new Random();
        int idx = random.nextInt(10) * 2;
        while (idx < 40) {
            accountList.add(
                    Account.builder()
                            .accountNumber(bankList[idx + 1])
                            .accountOwner(ownerName)
                            .bank(bankList[idx])
                            .balance(random.nextInt(1000) * 1000)
                            .build()
            );
            idx += (random.nextInt(10) + 1) * 2;
        }
        List<Account> saved = accountRepository.saveAll(accountList);
        if (saved == null) {
            throw new AccountException(ErrorCode.ACCOUNT_NOT_SAVED);
        }
    }

    private String[] bankList = {
            "카카오뱅크", "3333039245667", "농협은행", "4529938120488",
            "국민은행", "62759433128599", "신한은행", "110405017884",
            "우리은행", "8042334509426", "IBK기업은행", "82803587261454",
            "하나은행", "30167813092987", "대구은행", "902764492136",
            "도이치은행", "6988472302", "부산은행", "4920076349282",
            "씨티은행", "367808824649", "우체국은행", "30248710434185",
            "케이뱅크", "458669104827", "토스뱅크", "100842876694",
            "SC제일은행", "61745604237", "미래에셋증권", "84930667294",
            "한국투자증권", "84757823656838", "NH투자증권", "7684922461",
            "하나금융투자증권", "4562375481", "유인타증권", "47201867421"
    };


}

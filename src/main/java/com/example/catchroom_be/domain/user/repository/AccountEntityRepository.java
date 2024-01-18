package com.example.catchroom_be.domain.user.repository;

import com.example.catchroom_be.domain.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountEntityRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountNumberAndAccountOwnerAndBankName(String accountNumber, String accountOwner,String bankName);

}

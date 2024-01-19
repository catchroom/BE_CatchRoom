package com.example.catchroom_be.domain.user.repository;

import com.example.catchroom_be.domain.user.entity.DepositDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositDetailsRepository extends JpaRepository<DepositDetails,Long> {

}

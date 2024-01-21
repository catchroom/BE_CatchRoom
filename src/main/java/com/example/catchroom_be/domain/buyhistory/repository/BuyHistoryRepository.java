package com.example.catchroom_be.domain.buyhistory.repository;

import com.example.catchroom_be.domain.buyhistory.entity.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {
}

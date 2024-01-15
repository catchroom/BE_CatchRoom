package com.example.catchroom_be.domain.orderhistory.repository;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}

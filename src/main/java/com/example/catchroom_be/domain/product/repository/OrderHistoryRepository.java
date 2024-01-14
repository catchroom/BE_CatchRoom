package com.example.catchroom_be.domain.product.repository;

import com.example.catchroom_be.domain.product.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}

package com.example.catchroom_be.domain.buyhistory.repository;

import com.example.catchroom_be.domain.buyhistory.entity.BuyHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {

    public void deleteByProductId(Long id);
    Optional<BuyHistory> findByBuyerAndProduct(User buyer, Product product);

}

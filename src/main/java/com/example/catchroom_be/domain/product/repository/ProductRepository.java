package com.example.catchroom_be.domain.product.repository;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.type.DealState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCatchPriceStartDateBeforeAndDealStateAndIsAutoCatch(LocalDate today, DealState onSale, boolean isAutoCatch);

    List<Product> findAllByEndDateBeforeAndDealState(LocalDateTime now, DealState onSale);

    List<Product> findAllByDealState(DealState onSale);

    List<Product> findBySellerIdAndIsDeletedFalseOrderByCreatedAtDesc(Long id);


}

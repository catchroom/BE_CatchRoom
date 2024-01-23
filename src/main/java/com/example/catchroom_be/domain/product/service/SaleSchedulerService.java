package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.DealState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleSchedulerService {
    private final ProductRepository productRepository;
    @Scheduled(cron = "0 0 0 * * *",zone = "Asia/Seoul")
    @Transactional
    public void applyCatchPrice() {
        List<Product> productList = productRepository
            .findAllByCatchPriceStartDateBeforeAndDealStateAndIsAutoCatch(LocalDate.now(),DealState.ONSALE,true);
        for (Product product : productList) {
            product.updateIsCatch(product.getCatchPrice());
        }
    }

    @Scheduled(cron = "0 * * * * *",zone = "Asia/Seoul")
    @Transactional
    public void applyProductEndDate() {
        List<Product> productList = productRepository
            .findAllByEndDateBeforeAndDealState(LocalDateTime.now(),DealState.ONSALE);
        for (Product product : productList) {
            product.updateDealState(DealState.EXPIRED);
        }
    }

    @Scheduled(cron = "0 0 15 * * ?",zone = "Asia/Seoul")
    @Transactional
    public void applyProductUnsold() {
        List<Product> productList = productRepository
            .findAllByEndDateBeforeAndDealState(LocalDateTime.now(),DealState.ONSALE);
        List<Product> checkInProductList = new ArrayList<Product>();
        for (Product product : productList) {
            if (product.getOrderHistory().getCheckIn().equals(LocalDate.now())) {
                checkInProductList.add(product);
            }
        }
        for (Product product : checkInProductList) {
            product.updateDealState(DealState.UNSOLD);
        }
    }
}

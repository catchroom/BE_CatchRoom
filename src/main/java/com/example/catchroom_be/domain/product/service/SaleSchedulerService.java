package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleSchedulerService {
    private final ProductRepository productRepository;
    @Scheduled(cron = "0 0 0 * * *")
    public void applyCatchPrice() {

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void applyProductEndDate() {

    }
}

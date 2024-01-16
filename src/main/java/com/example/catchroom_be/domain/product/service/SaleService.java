package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.dto.response.SaleGetDetailInfoResponse;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    public SaleGetDetailInfoResponse findProductDetailInfo(Long orderHistroyId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistroyId).orElseThrow(IllegalArgumentException::new);
        return SaleGetDetailInfoResponse.fromEntity(orderHistory);
    }
}

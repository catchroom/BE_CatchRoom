package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.dto.request.SaleRegistRequest;
import com.example.catchroom_be.domain.product.dto.response.SaleEditResponse;
import com.example.catchroom_be.domain.product.dto.response.SaleGetDetailInfoResponse;
import com.example.catchroom_be.domain.product.dto.response.SaleRegistResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    @Transactional(readOnly = true)
    public SaleGetDetailInfoResponse findProductDetailInfo(Long orderHistroyId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistroyId).orElseThrow(IllegalArgumentException::new);
        return SaleGetDetailInfoResponse.fromEntity(orderHistory);
    }
    @Transactional
    public SaleRegistResponse registerProduct(SaleRegistRequest saleRegisterRequest) {
        OrderHistory orderHistory = orderHistoryRepository.findById(saleRegisterRequest.getOrderHistoryId()).orElseThrow(IllegalArgumentException::new);
        orderHistory.updateSaleState(true);
        Product product = productRepository.save(saleRegisterRequest.toEntity(orderHistory));
        return SaleRegistResponse.fromEntity(product);
    }
    @Transactional
    public SaleEditResponse editProduct(Long productId, SaleEditRequest saleEditRequest) {
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        product.updateProduct(saleEditRequest);
        return SaleEditResponse.fromEntity(product);
    }
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        OrderHistory orderHistory = orderHistoryRepository.findById(product.getOrderHistory().getId()).orElseThrow(IllegalArgumentException::new);
        orderHistory.updateSaleState(false);
        productRepository.deleteById(productId);
    }
}

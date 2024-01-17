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
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final UserEntityRepository userRepository;
    @Transactional(readOnly = true)
    public SaleGetDetailInfoResponse findProductDetailInfo(Long orderHistroyId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistroyId).orElseThrow(IllegalArgumentException::new);
        return SaleGetDetailInfoResponse.fromEntity(orderHistory);
    }
    @Transactional
    public SaleRegistResponse registerProduct(SaleRegistRequest saleRegisterRequest, User user) {
        OrderHistory orderHistory = orderHistoryRepository.findById(saleRegisterRequest.getOrderHistoryId()).orElseThrow(IllegalArgumentException::new);
        orderHistory.updateSaleState(true);
        Product product = productRepository.save(saleRegisterRequest.toEntity(orderHistory,user));
        return SaleRegistResponse.fromEntity(product);
    }
    @Transactional
    public SaleEditResponse editProduct(Long productId, SaleEditRequest saleEditRequest, User user) {
        User loginUser = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        if (loginUser.getId().equals(product.getSeller().getId())) {
            product.updateProduct(saleEditRequest);
        } else {
            throw new IllegalArgumentException("상품 등록자만 수정할 수 있습니다."); //TODO 예외처리 때 마무리
        }
        return SaleEditResponse.fromEntity(product);
    }
    @Transactional
    public void deleteProduct(Long productId, User user) {
        User loginUser = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        if (loginUser.getId().equals(product.getSeller().getId())) {
            OrderHistory orderHistory = orderHistoryRepository.findById(product.getOrderHistory().getId()).orElseThrow(IllegalArgumentException::new);
            orderHistory.updateSaleState(false);
            productRepository.deleteById(productId);
        } else {
            throw new IllegalArgumentException("상품 등록자만 삭제할 수 있습니다."); //TODO 예외처리 때 마무리
        }

    }
}

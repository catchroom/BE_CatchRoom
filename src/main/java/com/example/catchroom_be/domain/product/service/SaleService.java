package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.dto.request.SaleRegistRequest;
import com.example.catchroom_be.domain.product.dto.response.SaleEditResponse;
import com.example.catchroom_be.domain.product.dto.response.SaleGetAllInfoResponse;
import com.example.catchroom_be.domain.product.dto.response.SaleGetYanoljaReservationResponse;
import com.example.catchroom_be.domain.product.dto.response.SaleRegistResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.exception.SaleException;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final UserEntityRepository userRepository;
    @Transactional(readOnly = true)
    public SaleGetYanoljaReservationResponse findYanoljaReservationInfo(Long orderHistroyId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistroyId)
            .orElseThrow(() -> new SaleException(ErrorCode.ORDERHISTROY_NOT_FOUND));
        return SaleGetYanoljaReservationResponse.fromEntity(orderHistory);
    }
    @Transactional
    public SaleRegistResponse registerProduct(SaleRegistRequest saleRegisterRequest, User user) {
        OrderHistory orderHistory = validatedOrderHistoryOwner(saleRegisterRequest, user);
        validatedSaleEndDate(saleRegisterRequest.getEndDate(),orderHistory.getCheckIn());
        if (saleRegisterRequest.getIsAutoCatch()) {
            validatedAutoCatchDate(saleRegisterRequest.getCatchPriceStartDate(),saleRegisterRequest.getEndDate());
        }

        if (orderHistory.isSale()) {
            throw new SaleException(ErrorCode.DUPLICATED_REGIST_PRODUCT);
        }
        orderHistory.updateSaleState(true);
        Product product = productRepository.save(saleRegisterRequest.toEntity(orderHistory,user));
        return SaleRegistResponse.fromEntity(product);
    }
    @Transactional
    public SaleEditResponse editProduct(Long productId, SaleEditRequest saleEditRequest, User user) {
        Product product = validatedProductOwner(productId, user);
        validatedSaleEndDate(saleEditRequest.getEndDate(),product.getOrderHistory().getCheckIn());
        if (saleEditRequest.getIsAutoCatch()) {
            validatedAutoCatchDate(saleEditRequest.getCatchPriceStartDate(),saleEditRequest.getEndDate());
        }
        if ((product.getDealState().equals(DealState.UNSOLD) || product.getDealState().equals(DealState.EXPIRED))
            && product.getEndDate().isBefore(LocalDateTime.now()) ) {
            product.updateDealState(DealState.ONSALE);
        }
        product.updateProduct(saleEditRequest);
        return SaleEditResponse.fromEntity(product);
    }
    @Transactional
    public void deleteProduct(Long productId, User user) {
        Product product = validatedProductOwner(productId, user);
        OrderHistory orderHistory = orderHistoryRepository.findById(product.getOrderHistory().getId())
            .orElseThrow(() -> new SaleException(ErrorCode.ORDERHISTROY_NOT_FOUND));
        orderHistory.updateSaleState(false);
        product.setIsDeleted(true);
    }
    @Transactional(readOnly = true)
    public SaleGetAllInfoResponse findProductAllInfo(Long productId, User user) {
        Product product = validatedProductOwner(productId, user);
        if (product.getOrderHistory().getCheckIn().isBefore(LocalDate.now())) {
            throw new SaleException(ErrorCode.CHECKIN_DATE_ALREADY_EXPIRED);
        }
        if (product.getIsDeleted()) {
            throw new SaleException(ErrorCode.PRODUCT_ALREADY_DELETED);
        }
        return SaleGetAllInfoResponse.fromEntity(product);
    }
    @NotNull
    private Product validatedProductOwner(Long productId, User user) {
        User loginUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new SaleException(ErrorCode.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new SaleException(ErrorCode.PRODUCT_NOT_FOUND));
        if (!loginUser.getId().equals(product.getSeller().getId())) {
            throw new SaleException(ErrorCode.INVALID_PRODUCT_OWNER);
        }
        return product;
    }
    @NotNull
    private OrderHistory validatedOrderHistoryOwner(SaleRegistRequest saleRegisterRequest, User user) {
        OrderHistory orderHistory = orderHistoryRepository.findById(saleRegisterRequest.getOrderHistoryId())
            .orElseThrow(() -> new SaleException(ErrorCode.ORDERHISTROY_NOT_FOUND));
        if (orderHistory.getUser().getId() != user.getId()) {
            throw new SaleException(ErrorCode.INVALID_PRODUCT_OWNER);
        }
        return orderHistory;
    }
    private void validatedSaleEndDate(LocalDateTime endDate, LocalDate checkIn) {

        if (endDate.isBefore(LocalDateTime.now())) {
            throw new SaleException(ErrorCode.INVALID_REGIST_TIME_BEFORE_NOW);
        } else if (endDate.isAfter(checkIn.plusDays(1).atStartOfDay())) {
            throw new SaleException(ErrorCode.INVALID_REGIST_TIME_AFTER_CHECKIN);
        }
    }

    private void validatedAutoCatchDate(LocalDate autoCatchDate, LocalDateTime endDate) {

        if (autoCatchDate.isBefore(LocalDate.now())) {
            throw new SaleException(ErrorCode.INVALID_AUTOCATCH_PRICE_BEFORE_NOW);
        } else if (autoCatchDate.isAfter(endDate.toLocalDate())) {
            throw new SaleException(ErrorCode.INVALID_AUTOCATCH_PRICE_AFTER_ENDDATE);
        }
    }
}

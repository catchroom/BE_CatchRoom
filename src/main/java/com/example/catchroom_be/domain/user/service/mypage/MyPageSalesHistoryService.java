package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.repository.AccommodationRepository;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.review.entity.Review;
import com.example.catchroom_be.domain.review.enumlist.ReviewStatusType;
import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.review.repository.ReviewRepository;
import com.example.catchroom_be.domain.user.dto.response.SalesHistoryDoneResponse;
import com.example.catchroom_be.domain.user.dto.response.SalesHistoryNowResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyPageSalesHistoryService {
    private final ProductRepository productRepository;
    private final AccommodationRepository accommodationRepository;
    private final ReviewRepository reviewEntityRepository;


    @Transactional
    public List<SalesHistoryNowResponse> salesHistoryNowService(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<Product> products = productRepository.findBySellerIdAndIsDeletedFalseOrderByCreatedAtDesc(userId);

        if(products.isEmpty()) {
            throw new UserException(ErrorCode.MYPAGE_SALESLIST_FIND_ERROR);
        }

        List<SalesHistoryNowResponse> responses = new ArrayList<>();

        for (Product product : products) {
            if (product.getDealState().name().equals("ONSALE")) {
                OrderHistory orderHistory = product.getOrderHistory();

                SalesHistoryNowResponse response = new SalesHistoryNowResponse();
                response.fromProduct(orderHistory.getCheckIn(), orderHistory.getCheckOut()
                        , product.getCreatedAt(), product.getEndDate(), product.getSellPrice(), product.getIsCatch(), orderHistory.getId());
                Accommodation accommodation = accommodationRepository.findById(orderHistory.getAccommodation().getId())
                        .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_SALESLIST_FIND_ERROR));

                response.fromAccommodation(accommodation.getName(), accommodation.getThumbnailUrl());
                responses.add(response);
            }
        }

        return responses;
    }
    @Transactional
    public List<SalesHistoryDoneResponse> salesHistoryDoneService(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        List<Product> products = productRepository.findBySellerIdAndIsDeletedFalseOrderByCreatedAtDesc(userId);

        if(products.isEmpty()) {
            throw new UserException(ErrorCode.MYPAGE_SALESLIST_FIND_ERROR);
        }

        List<SalesHistoryDoneResponse> responses = new ArrayList<>();

        for (Product product : products) {
            if (!product.getDealState().name().equals("ONSALE")) {
                OrderHistory orderHistory = product.getOrderHistory();

                Long reviewId = Optional.ofNullable(product.getReview())
                        .filter(review -> review.getType().equals(ReviewType.SELL))
                        .map(Review::getId)
                        .orElse(null);

                Boolean reviewDeleteType = Optional.ofNullable(product.getReview())
                        .map(Review::getReviewDeleteType)// getType() 호출 전에 null인지 확인
                        .orElse(null);

                String reviewStatusType = null;

                if (reviewDeleteType == null || !reviewDeleteType) {
                    reviewStatusType = setReviewStatusType(product,reviewId);
                }
                else {
                    reviewStatusType = ReviewStatusType.REVIEWDELETE.getType();
                }

                SalesHistoryDoneResponse response = new SalesHistoryDoneResponse();
                response.fromProduct(orderHistory.getCheckIn(), orderHistory.getCheckOut()
                        , product.getCreatedAt(), product.getEndDate(), product.getSellPrice(),
                        product.getIsCatch(), orderHistory.getId(), product.getDealState().name(), reviewId,reviewStatusType);
                Accommodation accommodation = accommodationRepository.findById(orderHistory.getAccommodation().getId())
                        .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_SALESLIST_FIND_ERROR));

                response.fromAccommodation(accommodation.getName(), accommodation.getThumbnailUrl());
                responses.add(response);
            }
        }

        return responses;
    }

    public String setReviewStatusType(Product product,Long reviewId) {
        if (reviewId == null) {
            LocalDateTime createdAt = product.getCreatedAt();
            LocalDateTime fourteenDaysAgo = LocalDateTime.now().minusDays(14);

            if (createdAt.isAfter(fourteenDaysAgo)) {
                return ReviewStatusType.REVIEWCANWIRTE.getType(); // "리뷰 작성 가능"
            } else {
                return ReviewStatusType.REVIEWWIRTEEXPIRE.getType(); // "리뷰 작성기한 만료"
            }
        }
        else {
            return ReviewStatusType.REVIEWWRTIEN.getType(); //리뷰 작성 완료
        }
    }




}

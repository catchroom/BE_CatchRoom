package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.repository.AccommodationRepository;
import com.example.catchroom_be.domain.buyhistory.entity.BuyHistory;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.review.entity.Review;
import com.example.catchroom_be.domain.review.enumlist.ReviewStatusType;
import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.user.dto.response.PurChaseHistoryResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPagePurchaseHistoryService {
    private final UserEntityRepository userEntityRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final AccommodationRepository accommodationRepository;

    public List<PurChaseHistoryResponse> purchaseHistoryService(@AuthenticationPrincipal User user) {
           Long id = user.getId();
           Optional<User> newOptionalUser = Optional.ofNullable(userEntityRepository.findById(id)
                   .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PURCHASE_LIST_FIND_ERROR)));

           User newUser = newOptionalUser.get();

           List<BuyHistory> buyHistoryList = newUser.getBuyHistories();
           buyHistoryList.sort(Comparator.comparing(BuyHistory::getCreatedAt).reversed());

           List<PurChaseHistoryResponse> purChaseHistoryResponseList = new ArrayList<>();
           for (BuyHistory e : buyHistoryList) {
                  PurChaseHistoryResponse purChaseHistoryResponse = new PurChaseHistoryResponse();
                  LocalDateTime buyDate = e.getCreatedAt();
                 Long buyHistoryId = e.getId();
                  Product product = e.getProduct();
                  Boolean isCatch = product.getIsCatch();
                  Integer buyPrice = product.getSellPrice();
                  Long reviewId = Optional.ofNullable(product.getReview())
                       .filter(review -> review.getType().equals(ReviewType.BUY))
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

               Long orderHistoryId = e.getProduct().getOrderHistory().getId();
                  Long accmmodationId = e.getProduct().getOrderHistory().getAccommodation().getId();
                  Optional<OrderHistory> optionalOrderHistory = Optional.ofNullable(orderHistoryRepository.findById(orderHistoryId)
                          .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PURCHASE_LIST_FIND_ERROR)));

                  OrderHistory orderHistory = optionalOrderHistory.get();
                  Optional<Accommodation> optionalAccommodation = Optional.ofNullable(accommodationRepository.findById(accmmodationId)
                          .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_PURCHASE_LIST_FIND_ERROR)));

                  Accommodation accommodation = optionalAccommodation.get();

                  LocalDate checkIn = orderHistory.getCheckIn();
                  LocalDate checkOut = orderHistory.getCheckOut();
                  String accmmodationName = accommodation.getName();
                  String thumbNaialUrl = accommodation.getThumbnailUrl();
                  Long productId = e.getProduct().getId();

                  purChaseHistoryResponse.fromEntity(buyDate,isCatch,buyPrice,
                          checkIn,checkOut,accmmodationName,thumbNaialUrl,
                          reviewId,reviewStatusType,productId,buyHistoryId);

                  purChaseHistoryResponseList.add(purChaseHistoryResponse);


           }
        return purChaseHistoryResponseList;
    }

    public String setReviewStatusType(Product product,Long reviewId) {
        if (reviewId == null) {
            LocalDateTime createdAt = product.getEndDate();
            LocalDateTime fourteenDaysAgo = LocalDateTime.now().minusDays(14);

            if (createdAt.isAfter(fourteenDaysAgo)) {
                return ReviewStatusType.REVIEWCANWIRTE.getType(); // "리뷰 작성 가능"
            } else {
                return ReviewStatusType.REVIEWWIRTEEXPIRE.getType(); // "리뷰 작성기한 만료"
            }
        }
        else if (reviewId != null) {
            if (product.getReview().getReviewDeleteType()) {
                return ReviewStatusType.REVIEWDELETE.getType(); //리뷰 삭제 완료
            }
            else {
                return ReviewStatusType.REVIEWWRTIEN.getType(); //리뷰 작성 완료
        }
            
        }
        return ReviewStatusType.REVIEWWRTIEN.getType(); //리뷰 작성 가능을 기본으로
    }
}

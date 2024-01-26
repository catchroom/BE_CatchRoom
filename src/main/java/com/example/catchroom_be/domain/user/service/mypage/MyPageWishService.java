package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.repository.AccommodationRepository;
import com.example.catchroom_be.domain.accommodation.repository.RoomRepository;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.orderhistory.repository.OrderHistoryRepository;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.user.dto.response.WishResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.wish.entity.Wish;
import com.example.catchroom_be.domain.wish.repository.WishRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageWishService {


    private final OrderHistoryRepository orderHistoryRepository;
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<WishResponse> wishFindService(@AuthenticationPrincipal User user) {
        Long id = user.getId();

        // 사용자가 찜한 모든 Product를 조회
        List<Wish> wishList = wishRepository.findAllByUserId(id);

        List<WishResponse> wishResponseList = new ArrayList<>();

        for (Wish wish : wishList) {
            Long productId = wish.getProduct().getId();


            Optional<Product> optionalProduct = Optional.ofNullable(productRepository.findById(productId)
                    .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_WISHLIST_FIND_ERROR)));

            Product product = optionalProduct.get();

            Long orderHistoryId = product.getOrderHistory().getId();

            OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
                    .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_WISHLIST_FIND_ERROR));


            String accommodationName = orderHistory.getAccommodation().getName();
            LocalDate checkIn = orderHistory.getCheckIn();
            LocalDate checkOut = orderHistory.getCheckOut();
            boolean isCatch = product.getIsCatch();
            Integer sellPrice = product.getSellPrice();
            String roomName = orderHistory.getRoom().getName();
            double discountRate = product.getDiscountRate();
            double discountPrice = sellPrice * (1 - (discountRate / 100));
            Long wishId = wish.getId();


            WishResponse wishResponse = new WishResponse(accommodationName,checkIn
                    ,checkOut,isCatch,roomName,sellPrice,
                    discountRate,discountPrice,wishId,productId);

            wishResponseList.add(wishResponse);
        }

        return wishResponseList;
    }

    @Transactional
    public void wishDeleteService(@AuthenticationPrincipal User user, Long id) {
        Long userId = user.getId();

        Optional<Wish> optionalWish = Optional.ofNullable(wishRepository.findByIdAndUserId(id,userId)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_WISHLIST_DELETE_ERROR)));

        Wish wish = optionalWish.get();

        wishRepository.deleteById(id);
    }




}




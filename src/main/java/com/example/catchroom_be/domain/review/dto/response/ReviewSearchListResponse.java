package com.example.catchroom_be.domain.review.enumlist;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.review.entity.Review;
import com.example.catchroom_be.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewSearchListResponse {

    private Long size;
    private boolean nextPage;
    private List<ReviewSearchResponse> list;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewSearchResponse {

        private String productName;

        private String image;

        private String userName;

        private LocalDateTime date;

        private String content;

        private String region;

        public static ReviewSearchResponse fromEntity(Review review) {
            Product product = review.getProduct();
            User user = review.getUser();
            OrderHistory orderHistory = product.getOrderHistory();
            Accommodation accommodation = orderHistory.getAccommodation();

            return ReviewSearchResponse.builder()
                    .productName(accommodation.getName())
                    .image(accommodation.getThumbnailUrl())
                    .userName(user.getNickName())
                    .date(review.getCreatedAt())
                    .content(review.getContent())
                    .region(accommodation.getRegion())
                    .build();
        }

    }
}

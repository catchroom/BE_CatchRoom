package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ProductSearchListResponse {
    private int size;
    private List<ProductSearchResponse> list;
    private String message;
//    private boolean nextPage;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductSearchResponse {
        private Long productId;

        private String accommodationName;

        private String roomName;

        private String image;

        private LocalDate checkIn;

        private LocalDate checkOut;

        private Boolean catchType;

        private Integer originalPrice;

        private Integer discountRate;

        private Integer sellPrice;

        private String latitude;

        private String longitude;

        public static ProductSearchResponse fromEntity(Product product) {
            return ProductSearchResponse.builder()
                    .productId(product.getId())
                    .accommodationName(product.getAccommodationName())
                    .image(product.getOrderHistory().getAccommodation().getThumbnailUrl())
                    .checkIn(product.getOrderHistory().getCheckIn())
                    .checkOut(product.getOrderHistory().getCheckOut())
                    .catchType(product.getIsCatch())
                    .roomName(product.getOrderHistory().getRoom().getName())
                    .originalPrice(product.getOrderHistory().getPrice())
                    .discountRate(product.getDiscountRate())
                    .sellPrice(product.getSellPrice())
                    .latitude(product.getOrderHistory().getAccommodation().getLatitude())
                    .longitude(product.getOrderHistory().getAccommodation().getLongitude())
                    .build();
        }


    }
}

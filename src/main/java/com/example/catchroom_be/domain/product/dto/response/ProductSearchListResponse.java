package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductSearchListResponse {
    private Long size;
    private boolean nextPage;
    private List<ProductSearchResponse> list;
    private String message;


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

        private String region;

        public static ProductSearchResponse fromEntity(Product product) {
            return ProductSearchResponse.builder()
                    .productId(product.getId())
                    .accommodationName(product.getAccommodationName())
                    .region(product.getOrderHistory().getAccommodation().getRegion())
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

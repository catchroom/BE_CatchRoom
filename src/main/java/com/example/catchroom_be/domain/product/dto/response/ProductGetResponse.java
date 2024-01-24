package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ProductGetResponse {
    private Long seller_id;
    private String accommodationName;
    private UserIdentity userIdentity;
    private String chatRoomNumber;
    private List<?> accommodationUrl;
    private String roomType;
    private List<?> roomUrl;
    private Double star;
    private int originalPrice;
    private int discountRate;
    private int sellPrice;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String introduction;
    private String latitude;
    private String longitude;
    private String address;
    private int roomNormalCapacity;
    private int roomMaxCapacity;
    private String[] accommodationService;
    private String roomService;
    private int totalRoomCapacity;

    public static ProductGetResponse fromEntity(Product product, UserIdentity checkUserIdentity, List<String> chatRoomId) {
        if (chatRoomId.isEmpty()) {
            return ProductGetResponse.builder()
                .seller_id(product.getSeller().getId())
                .accommodationName(product.getAccommodationName())
                .userIdentity(checkUserIdentity)
                .chatRoomNumber("0")
                .accommodationUrl(product.getOrderHistory().getAccommodation().getAccommodationImageList())
                .roomType(product.getOrderHistory().getRoom().getName())
                .roomUrl(product.getOrderHistory().getRoom().getRoomImageList())
                .star(product.getOrderHistory().getAccommodation().getStar())
                .originalPrice(product.getOrderHistory().getPrice())
                .discountRate(product.getDiscountRate())
                .sellPrice(product.getSellPrice())
                .checkIn(product.getOrderHistory().getCheckIn())
                .checkOut(product.getOrderHistory().getCheckOut())
                .introduction(product.getIntroduction())
                .latitude(product.getOrderHistory().getAccommodation().getLatitude())
                .longitude(product.getOrderHistory().getAccommodation().getLongitude())
                .address(product.getOrderHistory().getAccommodation().getAddress())
                .roomNormalCapacity(product.getOrderHistory().getRoom().getNormalCapacity())
                .roomMaxCapacity(product.getOrderHistory().getRoom().getMaxCapacity())
                .accommodationService(product.getOrderHistory().getAccommodation().getService().split(","))
                .build();
        } else {
            return ProductGetResponse.builder()
                .seller_id(product.getSeller().getId())
                .accommodationName(product.getAccommodationName())
                .userIdentity(checkUserIdentity)
                .chatRoomNumber(chatRoomId.get(0))
                .accommodationUrl(product.getOrderHistory().getAccommodation().getAccommodationImageList())
                .roomType(product.getOrderHistory().getRoom().getName())
                .roomUrl(product.getOrderHistory().getRoom().getRoomImageList())
                .star(product.getOrderHistory().getAccommodation().getStar())
                .originalPrice(product.getOrderHistory().getPrice())
                .discountRate(product.getDiscountRate())
                .sellPrice(product.getSellPrice())
                .checkIn(product.getOrderHistory().getCheckIn())
                .checkOut(product.getOrderHistory().getCheckOut())
                .introduction(product.getIntroduction())
                .latitude(product.getOrderHistory().getAccommodation().getLatitude())
                .longitude(product.getOrderHistory().getAccommodation().getLongitude())
                .address(product.getOrderHistory().getAccommodation().getAddress())
                .roomNormalCapacity(product.getOrderHistory().getRoom().getNormalCapacity())
                .roomMaxCapacity(product.getOrderHistory().getRoom().getMaxCapacity())
                .accommodationService(product.getOrderHistory().getAccommodation().getService().split(","))
                .build();
        }

    }
}

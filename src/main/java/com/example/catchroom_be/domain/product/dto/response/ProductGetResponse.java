package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import lombok.*;

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

    public static ProductGetResponse fromEntity(Product product, UserIdentity checkUserIdentity, List<String> chatRoomId) {
        if (chatRoomId.isEmpty()) {
            return ProductGetResponse.builder()
                .seller_id(product.getSeller().getId())
                .accommodationName(product.getAccommodationName())
                .userIdentity(checkUserIdentity)
                .chatRoomNumber("0")
                .build();
        } else {
            return ProductGetResponse.builder()
                .seller_id(product.getSeller().getId())
                .accommodationName(product.getAccommodationName())
                .userIdentity(checkUserIdentity)
                .chatRoomNumber(chatRoomId.get(0))
                .build();
        }

    }
}

package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SaleRegistResponse {
    private String accommodationName;
    //TODO 응답 방식 고려
    public static SaleRegistResponse fromEntity(Product product) {
        return SaleRegistResponse.builder()
            .accommodationName(product.getAccommodationName())
            .build();
    }
}

package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.product.entity.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SaleRegistResponse {
    private Long id;
    private String accommodationName;
    public static SaleRegistResponse fromEntity(Product product) {
        return SaleRegistResponse.builder()
            .id(product.getId())
            .accommodationName(product.getAccommodationName())
            .build();
    }
}

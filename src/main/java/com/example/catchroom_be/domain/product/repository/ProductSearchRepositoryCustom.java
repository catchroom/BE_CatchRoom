package com.example.catchroom_be.domain.product.repository;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse.ProductSearchResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.*;

public interface ProductSearchRepositoryCustom {
    ProductSearchListResponse search(
            List<AccommodationType> accommodationType, List<String> regionList, int pax,
            LocalDate checkInStart, LocalDate checkInEnd, ProductSortType filter, Pageable pageable
    );
}

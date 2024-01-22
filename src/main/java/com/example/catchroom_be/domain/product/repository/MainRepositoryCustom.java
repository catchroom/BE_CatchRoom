package com.example.catchroom_be.domain.product.repository;

import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse.ProductSearchResponse;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MainRepositoryCustom {

    ProductSearchListResponse getCatchMain();

    ProductSearchListResponse getCatchAll(ProductSortType filter,
                                          List<String> regionList,
                                          Pageable pageable);

    ProductSearchListResponse getCheckInMain(LocalDate date);

    ProductSearchListResponse getCheckInAll(LocalDate date,
                                            ProductSortType filter,
                                            List<String> regionList,
                                            Pageable pageable);
}

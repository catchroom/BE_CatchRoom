package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil;
import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import com.example.catchroom_be.domain.accommodation.type.RegionUtil;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse.ProductSearchResponse;
import com.example.catchroom_be.domain.product.repository.ProductSearchRepository;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductSearchRepository productSearchRepository;

    private final String ALL_REGION = "all"; //전체 지역 검색

    /**
     * 검색 결과 반환
     */
    @Transactional(readOnly = true)
    public ProductSearchListResponse getSearchList(
            String accommodationTypeStr, String regionStr, int pax,
            LocalDate checkInStart, LocalDate checkInEnd, ProductSortType filter,
            Pageable pageable
    ) {
        // 1. 숙소 타입과 지역들을 List로 바꿈.

        List<AccommodationType> accommodationTypeList =
                AccommodationTypeUtil.getAccommodationTypeList(accommodationTypeStr);

        List<String> regionList = new ArrayList<>();
        if (!regionStr.equals(ALL_REGION)) regionList = RegionUtil.getRegionList(regionStr);


        // 3. 검색 결과가 0개와 아닐때 다르게 보냄.

//        if (productList.size() == 0) {
//            return ProductSearchListResponse.builder().size(0).message("검색 결과가 없습니다.").build();
//        }


        return productSearchRepository.search(
                accommodationTypeList,
                regionList,
                pax,
                checkInStart,
                checkInEnd,
                filter,
                pageable);

    }


}

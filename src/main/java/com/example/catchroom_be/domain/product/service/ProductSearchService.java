package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil;
import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import com.example.catchroom_be.domain.accommodation.type.RegionUtil;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse.ProductSearchResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductSearchRepository;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService  {
    private final ProductSearchRepository productSearchRepository;

    private final String ALL_REGION = "all"; //전체 지역 검색

    /** 검색 결과 반환 */
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


        // 2. query Dsl 을 통해 검색

        List<ProductSearchResponse> productList = productSearchRepository.search(
                accommodationTypeList, regionList, pax, checkInStart, checkInEnd, filter, pageable
        );


        // 3. 검색 결과가 0개와 아닐때 다르게 보냄.

        if (productList.size() == 0) {
            return ProductSearchListResponse.builder().size(0).message("검색 결과가 없습니다.").build();
        }



        //4. TODO 무한 스크롤 구현 예정

//        boolean hasNext = false;
//        int totalPage = productPage.getTotalPages();
//
//        // 조회한 결과 개수가 요청한 페이지 사이즈보다 클 경우, next = true
//        if (productList.size() > pageable.getPageSize()) {
//            hasNext = true;
//            productList.remove(pageable.getPageSize());
//        }
//
//
        return ProductSearchListResponse.builder()
                .size(productList.size())
                .list(productList)
                .message("검색 완료 했습니다.")
                .build();

    }



}

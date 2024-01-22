package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.accommodation.type.RegionUtil;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.repository.MainRepository;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainRepository mainRepository;

    private final String ALL_REGION = "all"; //전체 지역 검색

    @Transactional(readOnly = true)
    public ProductSearchListResponse catchMain(Pageable pageable) {

        return mainRepository.getCatchMain(pageable);
    }

    @Transactional(readOnly = true)
    public ProductSearchListResponse catchAll(
            ProductSortType filter,
            String regionStr,
            Pageable pageable) {

        List<String> regionList = new ArrayList<>();
        if (!regionStr.equals(ALL_REGION)) regionList = RegionUtil.getRegionList(regionStr);

        return mainRepository.getCatchAll(filter, regionList, pageable);
    }

    @Transactional(readOnly = true)
    public ProductSearchListResponse checkInMain(LocalDate date, Pageable pageable) {

        return mainRepository.getCheckInMain(date, pageable);
    }

    @Transactional(readOnly = true)
    public ProductSearchListResponse checkInAll(LocalDate date,
                                                ProductSortType filter,
                                                String regionStr,
                                                Pageable pageable) {

        List<String> regionList = new ArrayList<>();
        if (!regionStr.equals(ALL_REGION)) regionList = RegionUtil.getRegionList(regionStr);

        return mainRepository.getCheckInAll(date, filter, regionList, pageable);
    }

}

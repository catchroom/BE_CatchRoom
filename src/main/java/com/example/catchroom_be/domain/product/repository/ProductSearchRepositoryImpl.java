package com.example.catchroom_be.domain.product.repository;

import com.example.catchroom_be.domain.accommodation.type.AccommodationTypeUtil.AccommodationType;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse.ProductSearchResponse;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.example.catchroom_be.domain.accommodation.entity.QAccommodation.accommodation;
import static com.example.catchroom_be.domain.accommodation.entity.QRoom.room;
import static com.example.catchroom_be.domain.orderhistory.entity.QOrderHistory.orderHistory;
import static com.example.catchroom_be.domain.product.entity.QProduct.product;
import static com.example.catchroom_be.domain.review.entity.QReview.review;

/**
 * 상품 조건 검색을 위한 queryDsl
 */
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public ProductSearchListResponse search(
            List<AccommodationType> accommodationTypeList, List<String> regionList, int pax,
            LocalDate checkInStart, LocalDate checkInEnd, ProductSortType filter, Pageable pageable
    ) {
        List<ProductSearchResponse> result = queryFactory.selectFrom(product)
                .leftJoin(product.review,review).fetchJoin()
                .innerJoin(product.orderHistory, orderHistory).fetchJoin()
                .innerJoin(orderHistory.room, room).fetchJoin()
                .innerJoin(orderHistory.accommodation, accommodation).fetchJoin()
                .where(eqRegionList(regionList), eqAccommodationTypeList(accommodationTypeList),
                        eqBetweenCheckInStartAndCheckInEnd(checkInStart, checkInEnd),
                        eqPaxIsLessThenMaxCapacity(pax))
                .orderBy(
                        getOrderTypeByProductSortType(filter)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductSearchResponse::fromEntity)
                .toList();


        long totalSize = queryFactory.selectFrom(product)
                .where(eqRegionList(regionList), eqAccommodationTypeList(accommodationTypeList),
                        eqBetweenCheckInStartAndCheckInEnd(checkInStart, checkInEnd),
                        eqPaxIsLessThenMaxCapacity(pax))
                .fetchCount();

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        boolean isNextPage = (pageNumber + 1) * pageSize < totalSize;

        return ProductSearchListResponse.builder()
                .size(totalSize)
                .nextPage(isNextPage)
                .list(result)
                .message("Success")
                .build();
    }

    private BooleanExpression eqRegionList(List<String> regionList) {
        // sql : where region =? or region =? (anyOf 는 or 와 같다)
        // 지역 리스트를 or 로 검색하는 방법
        // regionList = {"서울", "인천", "강원"} 일 경우, where region regexp "서울|인천|강원" 와 같음.
        return regionList != null ? Expressions.anyOf(
                regionList.stream().map(this::isEqRegion)
                        .toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isEqRegion(String region) {
        // sql : where region = ?
        return product.orderHistory.accommodation.region.eq(region);
    }

    private BooleanExpression eqAccommodationTypeList(List<AccommodationType> accommodationTypeList) {
        // sql : where accommodationType=? or accommodationType=? (anyOf 는 or 와 같다)
        return accommodationTypeList != null ? Expressions.anyOf(
                accommodationTypeList.stream().map(this::isEqAccommodationType)
                        .toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isEqAccommodationType(AccommodationType accommodationType) {
        // sql : where accommodationType = ?
        return product.orderHistory.accommodation.type.eq(accommodationType);
    }

    private BooleanExpression eqBetweenCheckInStartAndCheckInEnd(LocalDate start, LocalDate end) {
        // sql : where checkIn between (start, end)
        return product.orderHistory.checkIn.between(start, end);
    }

    private BooleanExpression eqPaxIsLessThenMaxCapacity(int pax) {
        // sql : where pax <= max_capacity (최대 인원 수보다 적은 인원 수 객실 반환)
        return product.orderHistory.room.maxCapacity.goe(pax);
    }


    private OrderSpecifier[] getOrderTypeByProductSortType(ProductSortType productSortType) {

        OrderSpecifier[] orderSpecifierList = new OrderSpecifier[4];
        switch (productSortType) {
            case HIGH_DISCOUNT -> {
                orderSpecifierList[0] = product.discountRate.desc();
                orderSpecifierList[1] = product.orderHistory.checkIn.asc();
                orderSpecifierList[2] = product.sellPrice.asc();
                orderSpecifierList[3] = product.createdAt.asc();
            }

            case NEAR_CHECKIN -> {
                orderSpecifierList[0] = product.orderHistory.checkIn.asc();
                orderSpecifierList[1] = product.discountRate.desc();
                orderSpecifierList[2] = product.sellPrice.asc();
                orderSpecifierList[3] = product.createdAt.asc();
            }

            case LOW_PRICE -> {
                orderSpecifierList[0] = product.sellPrice.asc();
                orderSpecifierList[1] = product.discountRate.desc();
                orderSpecifierList[2] = product.orderHistory.checkIn.asc();
                orderSpecifierList[3] = product.createdAt.asc();
            }

            case OLD_CREATED_DATE -> {
                orderSpecifierList[0] = product.createdAt.asc();
                orderSpecifierList[1] = product.orderHistory.checkIn.asc();
                orderSpecifierList[2] = product.discountRate.desc();
                orderSpecifierList[3] = product.sellPrice.asc();
            }
        }
        return orderSpecifierList;
    }

}

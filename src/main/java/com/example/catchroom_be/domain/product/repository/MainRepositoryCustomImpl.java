package com.example.catchroom_be.domain.product.repository;

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


@RequiredArgsConstructor
public class MainRepositoryCustomImpl implements MainRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public ProductSearchListResponse getCatchMain() {

        List<ProductSearchResponse> result = queryFactory.selectFrom(product)
                .leftJoin(product.review,review).fetchJoin()
                .innerJoin(product.orderHistory, orderHistory).fetchJoin()
                .innerJoin(orderHistory.room, room).fetchJoin()
                .innerJoin(orderHistory.accommodation, accommodation).fetchJoin()
                .where(isCatchTrue())
                .limit(10)
                .fetch()
                .stream()
                .map(ProductSearchResponse::fromEntity)
                .toList();

        return ProductSearchListResponse.builder()
                .size((long) result.size())
                .list(result)
                .nextPage(false)
                .message("Success")
                .build();
    }


    @Override
    public ProductSearchListResponse getCatchAll(
            ProductSortType filter,
            List<String> regionList,
            Pageable pageable) {

        List<ProductSearchResponse> result = queryFactory.selectFrom(product)
                .leftJoin(product.review,review).fetchJoin()
                .innerJoin(product.orderHistory, orderHistory).fetchJoin()
                .innerJoin(orderHistory.room, room).fetchJoin()
                .innerJoin(orderHistory.accommodation, accommodation).fetchJoin()
                .where(isCatchTrue().and(eqRegionList(regionList)))
                .orderBy(
                        getOrderTypeByProductSortType(filter))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductSearchResponse::fromEntity)
                .toList();

        long totalSize = queryFactory.selectFrom(product)
                .where(isCatchTrue().and(eqRegionList(regionList)))
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

    @Override
    public ProductSearchListResponse getCheckInMain(LocalDate date) {

        List<ProductSearchResponse> result = queryFactory.selectFrom(product)
                .leftJoin(product.review,review).fetchJoin()
                .innerJoin(product.orderHistory, orderHistory).fetchJoin()
                .innerJoin(orderHistory.room, room).fetchJoin()
                .innerJoin(orderHistory.accommodation, accommodation).fetchJoin()
                .where(isCheckInEqual(date))
                .limit(3)
                .fetch()
                .stream()
                .map(ProductSearchResponse::fromEntity)
                .toList();

        return ProductSearchListResponse.builder()
                .size((long) result.size())
                .list(result)
                .nextPage(false)
                .message("Success")
                .build();
    }

    @Override
    public ProductSearchListResponse getCheckInAll(
            LocalDate date,
            ProductSortType filter,
            List<String> regionList,
            Pageable pageable) {


        List<ProductSearchResponse> result = queryFactory.selectFrom(product)
                .leftJoin(product.review,review).fetchJoin()
                .innerJoin(product.orderHistory, orderHistory).fetchJoin()
                .innerJoin(orderHistory.room, room).fetchJoin()
                .innerJoin(orderHistory.accommodation, accommodation).fetchJoin()
                .where(isCheckInEqual(date).and(eqRegionList(regionList)))
                .orderBy(
                        getOrderTypeByProductSortType(filter))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductSearchResponse::fromEntity)
                .toList();

        long totalSize = queryFactory.selectFrom(product)
                .where(isCheckInEqual(date).and(eqRegionList(regionList)))
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

    private BooleanExpression isCatchTrue() {
        return product.isCatch.eq(true);
    }

    private BooleanExpression isEqRegion(String region) {
        return product.orderHistory.accommodation.region.eq(region);
    }

    private BooleanExpression eqRegionList(List<String> regionList) {
        return regionList != null ? Expressions.anyOf(
                regionList.stream().map(this::isEqRegion)
                        .toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isCheckInEqual(LocalDate date) {
        return orderHistory.checkIn.eq(date);
    }
}

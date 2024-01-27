package com.example.catchroom_be.domain.review.repository;

import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import com.example.catchroom_be.domain.review.enumlist.ReviewSearchListResponse;
import com.example.catchroom_be.domain.review.enumlist.ReviewSearchListResponse.ReviewSearchResponse;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.text.DateFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.catchroom_be.domain.accommodation.entity.QAccommodation.accommodation;
import static com.example.catchroom_be.domain.accommodation.entity.QRoom.room;
import static com.example.catchroom_be.domain.orderhistory.entity.QOrderHistory.orderHistory;
import static com.example.catchroom_be.domain.product.entity.QProduct.product;
import static com.example.catchroom_be.domain.review.entity.QReview.review;
import static com.example.catchroom_be.domain.user.entity.QUser.user;


@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ReviewSearchListResponse getReviewMain() {

        List<ReviewSearchResponse> result =
                queryFactory.selectFrom(review)
                        .innerJoin(review.product,product).fetchJoin()
                        .innerJoin(review.user,user).fetchJoin()
                        .innerJoin(product.orderHistory,orderHistory).fetchJoin()
                        .innerJoin(orderHistory.accommodation,accommodation).fetchJoin()
                        .limit(10)
                        .orderBy(getOrderType())
                        .fetch()
                        .stream()
                        .map(ReviewSearchResponse::fromEntity)
                        .toList();

        return ReviewSearchListResponse.builder()
                .size((long) result.size())
                .list(result)
                .nextPage(false)
                .build();
    }

    @Override
    public ReviewSearchListResponse getReviewAll(Pageable pageable) {

        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        List<ReviewSearchResponse> result =
                queryFactory.selectFrom(review)
                        .innerJoin(review.product,product).fetchJoin()
                        .innerJoin(review.user,user).fetchJoin()
                        .innerJoin(product.orderHistory,orderHistory).fetchJoin()
                        .innerJoin(orderHistory.accommodation,accommodation).fetchJoin()
                        .where(review.createdAt.after(sixMonthsAgo.atStartOfDay()))
                        .orderBy(getOrderType())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
                        .stream()
                        .map(ReviewSearchResponse::fromEntity)
                        .toList();

        long totalSize = queryFactory.selectFrom(review)
                .fetchCount();

        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        boolean isNextPage = (pageNumber + 1) * pageSize < totalSize;

        return ReviewSearchListResponse.builder()
                .size(totalSize)
                .nextPage(isNextPage)
                .list(result)
                .build();

    }

    private OrderSpecifier[] getOrderType() {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , review.createdAt
                , ConstantImpl.create("%Y-%m-%d"));

        OrderSpecifier[] orderSpecifierList = new OrderSpecifier[2];
        orderSpecifierList[0] = formattedDate.desc();
        orderSpecifierList[1] = review.content.length().desc();

        return orderSpecifierList;
    }
}

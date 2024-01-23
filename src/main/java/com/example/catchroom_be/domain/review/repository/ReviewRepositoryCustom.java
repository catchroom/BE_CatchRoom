package com.example.catchroom_be.domain.review.repository;

import com.example.catchroom_be.domain.product.dto.response.ProductSearchListResponse;
import com.example.catchroom_be.domain.review.enumlist.ReviewSearchListResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    ReviewSearchListResponse getReviewMain();

    ReviewSearchListResponse getReviewAll(Pageable pageable);
}

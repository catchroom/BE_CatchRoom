package com.example.catchroom_be.domain.review.repository;

import com.example.catchroom_be.domain.review.entity.Review;
import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.user.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom {

    @EntityGraph(attributePaths = {"user", "product.orderHistory.accommodation"})
    Optional<Review> findByIdAndType(Long id, ReviewType type);

    @EntityGraph(attributePaths = {"user", "product.orderHistory.accommodation"})
    Optional<Review> findById(Long ReviewId);

}

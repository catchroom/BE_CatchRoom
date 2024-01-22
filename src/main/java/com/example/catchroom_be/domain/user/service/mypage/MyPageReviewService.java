package com.example.catchroom_be.domain.user.service.mypage;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.QAccommodation;
import com.example.catchroom_be.domain.accommodation.repository.AccommodationRepository;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.review.entity.Review;
import com.example.catchroom_be.domain.review.enumlist.ReviewRefactType;
import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.review.repository.ReviewEntityRepository;
import com.example.catchroom_be.domain.user.dto.request.ReviewPostRequest;
import com.example.catchroom_be.domain.user.dto.request.ReviewRefactRequest;
import com.example.catchroom_be.domain.user.dto.response.ReviewResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageReviewService {

    private final ReviewEntityRepository reviewEntityRepository;
    private final AccommodationRepository accommodationRepository;
    private final ProductRepository productRepository;


    @Transactional
    public ReviewResponse reviewFindService(ReviewType type, Long id) {
        System.out.println(type);
        System.out.println(id);

        Optional<Review> optionalReview = reviewEntityRepository.findByIdAndType(id,type);

        if (!optionalReview.isPresent()) {
            throw new UserException(ErrorCode.MYPAGE_REVIEW_FIND_ERROR);
        }


        Review e = optionalReview.get();
        Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(e.getProduct().getOrderHistory().getAccommodation().getId());
        Accommodation accommodation = optionalAccommodation.get();
        String accommodationName = accommodation.getName();
        String content = e.getContent();
        Boolean isModify = false;

        LocalDateTime modifiedAt = e.getModifiedAt();
        if (modifiedAt != null) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(modifiedAt, now);
            long diff = Math.abs(duration.toHours());
            if (diff <= 48) {
                isModify = true;
            }
        }

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.fromEntity(accommodationName, content, isModify);

        return reviewResponse;
    }


    @Transactional
    public void reviewPostService(@AuthenticationPrincipal User user, ReviewPostRequest reviewPostRequest) {
        Long id = user.getId();

        Product product = productRepository.findById(reviewPostRequest.getProductId())
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_REVIEW_WRITE_ERROR));

        Review review = Review.builder()
                        .user(user)
                                .product(product)
                                        .content(reviewPostRequest.getContent())
                                                .type(reviewPostRequest.getType())
                .reviewRefactType(ReviewRefactType.REVIEWREFACTVALID)
                                                        .build();
        reviewEntityRepository.save(review);


    }

    @Transactional
    public void reviewRefactService(ReviewRefactRequest reviewRefactRequest) {

        Optional<Review> optionalProduct = Optional.ofNullable(reviewEntityRepository.findById(reviewRefactRequest.getReviewId())
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_REVIEW_REFACT_ERROR)));

        Review review = optionalProduct.get();

        review.refactReview(reviewRefactRequest.getContent(),reviewRefactRequest.getType(),ReviewRefactType.REVIEWREFACTVALID);

        reviewEntityRepository.save(review);


    }

    @Transactional
    public void reviewDeleteService(ReviewType type,Long id) {
        Optional<Review> optionalProduct = Optional.ofNullable(reviewEntityRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.MYPAGE_REVIEW_DELETE_ERROR)));
        Long reviewId = optionalProduct.get().getId();

        reviewEntityRepository.deleteById(reviewId);
    }
}

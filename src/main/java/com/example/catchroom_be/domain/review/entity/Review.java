package com.example.catchroom_be.domain.review.entity;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.review.enumlist.ReviewRefactType;
import com.example.catchroom_be.domain.review.enumlist.ReviewStatusType;
import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name ="review")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type" , nullable = false)
    private ReviewType type;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "review_delete_type",nullable = false)
    private Boolean reviewDeleteType;

    @Column(name = "review_put_type",nullable = false)
    private ReviewRefactType reviewRefactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public void refactReview(String content, ReviewType type, ReviewRefactType reviewRefactType) {
        this.content = content;
        this.type = type;
        this.reviewRefactType = reviewRefactType;
    }

    public void deleteReview() {
        this.reviewDeleteType = true;
    }


}

package com.example.catchroom_be.domain.wish.entity;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name = "wish")
@NoArgsConstructor
@AllArgsConstructor
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static Wish addWishList(User user, Product product) {
        Wish wish = new Wish();
        wish.product = product;
        wish.user = user;

        return wish;
    }
}

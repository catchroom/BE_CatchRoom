package com.example.catchroom_be.domain.wish.repository;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Wish findByUserAndProduct(User user, Product product);
}

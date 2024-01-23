package com.example.catchroom_be.domain.wish.repository;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Wish findByUserAndProduct(User user, Product product);

    List<Wish> findAllByUserId(Long userId);

    Optional<Wish> findByIdAndUserId(Long id, Long userId);
}

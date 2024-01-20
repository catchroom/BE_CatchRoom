package com.example.catchroom_be.domain.wish.service;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.wish.entity.Wish;
import com.example.catchroom_be.domain.wish.repository.WishRepository;
import com.example.catchroom_be.global.common.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    @Override
    @Transactional
    public ApiResponse setWishList(Long productId, User user) {

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        Wish wish = wishRepository.findByUserAndProduct(user, product);

        if (wish != null) {
            wishRepository.delete(wish);
            return ApiResponse.create(2042, "상품을 찜 목록에서 삭제했습니다.");
        }

        wishRepository.save(Wish.addWishList(user, product));

        return ApiResponse.create(2038, "상품을 찜 목록에 추가했습니다.");
    }


}

package com.example.catchroom_be.domain.wish.service;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;

public interface WishService {

    ApiResponse setWishList(Long productId, User user);
}

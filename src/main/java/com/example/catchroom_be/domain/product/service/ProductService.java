package com.example.catchroom_be.domain.product.service;


import com.example.catchroom_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.catchroom_be.domain.product.dto.response.ProductGetResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.exception.ProductException;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserEntityRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public ProductGetResponse findProduct(Long productId, User user) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        User productSeller = memberRepository.findById(product.getSeller().getId())
            .orElseThrow(() -> new ProductException(ErrorCode.USER_NOT_FOUND));
        UserIdentity viewerIdentity = validateUserEqualSeller(productSeller.getId(),user);
        List<String> chatRoomId = chatRoomRepository.findUniqueChatRoom(findLoginUserId(user), productSeller.getId(), product.getId());
        return ProductGetResponse.fromEntity(product,viewerIdentity,chatRoomId);
    }

    private UserIdentity validateUserEqualSeller(Long sellerId,User loginUser) {
        if (loginUser == null) {
            return UserIdentity.NONLOGINUSER;
        }
        else if (sellerId.equals(loginUser.getId())) {
            return UserIdentity.SELLER;
        } else {
            return UserIdentity.BUYER;
        }
    }

    private Long findLoginUserId(User user) {
        if (user == null) {
            return 0L;
        } else {
            return user.getId();
        }
    }


}

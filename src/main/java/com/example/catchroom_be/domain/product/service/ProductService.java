package com.example.catchroom_be.domain.product.service;


import com.example.catchroom_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.catchroom_be.domain.product.dto.response.ProductGetResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
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
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        User productSeller = memberRepository.findById(product.getSeller().getId()).orElseThrow(IllegalArgumentException::new);
        UserIdentity viewerIdentity = validateUserEqualSeller(productSeller.getId());
        List<String> chatRoomId = chatRoomRepository.findUniqueChatRoom(findLoginUserId(user), productSeller.getId(), product.getId());
        return ProductGetResponse.fromEntity(product,viewerIdentity,chatRoomId);
    }

    private UserIdentity validateUserEqualSeller(Long sellerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUserEmail = authentication.getName();
        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            return UserIdentity.NONLOGINUSER;
        }
        // 로그인 사용자가 판매자가 아니어야 함
        else if (loginUserEmail.equals(memberRepository.getReferenceById(sellerId).getEmail())) {
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

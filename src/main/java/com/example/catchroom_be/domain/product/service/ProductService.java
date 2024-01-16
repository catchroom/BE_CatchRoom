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
    public ProductGetResponse findProduct(Long id) {
        Product product = productRepository.getReferenceById(id);
        User sellMember = memberRepository.getReferenceById(product.getSeller().getId());
        UserIdentity checkUserIdentity = validateUserEqualSeller(sellMember.getId());

        List<String> chatRoomId = chatRoomRepository.findUniqueChatRoom(findLoginUserId(), sellMember.getId(), product.getId());
        return ProductGetResponse.fromEntity(product,checkUserIdentity,chatRoomId);
    }

    private UserIdentity validateUserEqualSeller(Long sellerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        String loginUserEmail = authentication.getName();

        if (loginUserEmail.equals("anonymousUser")) {
            return UserIdentity.NONLOGINUSER;
        }
        // 로그인 사용자가 판매자가 아니어야 함

        else if (!loginUserEmail.equals(memberRepository.getReferenceById(sellerId).getEmail())) {
            return UserIdentity.BUYER;
        } else {
            return UserIdentity.SELLER;
        }
    }

    private Long findLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        String loginUserEmail = authentication.getName();

        if (loginUserEmail.equals("anonymousUser")) {
            return 0L;
        } else {
            User member = memberRepository.findByEmail(loginUserEmail).orElseThrow();
            return member.getId();
        }
    }


}

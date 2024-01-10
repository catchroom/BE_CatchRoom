package com.example.catchroom_be.domain.product.service;

import com.example.catchroom_be.domain.product.dto.ProductGetResponse;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import com.example.catchroom_be.domain.test_user.entity.Member;
import com.example.catchroom_be.domain.test_user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public ProductGetResponse findProduct(Long id) {
        Product product = productRepository.getReferenceById(id);
        Member sellMember = memberRepository.getReferenceById(product.getSeller_id());
        UserIdentity checkUserIdentity = validateUserEqualSeller(sellMember.getId());
        return ProductGetResponse.fromEntity(product,checkUserIdentity);
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


}

package com.example.catchroom_be.domain.chatroom.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.catchroom_be.domain.chatroom.entity.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findUniqueChatRoom(Long loginUserId, Long sellerId, Long productId) {
        return queryFactory
            .select(chatRoom.chatRoomNumber)
            .from(chatRoom)
            .where(loginUserIdEq(loginUserId),
                sellerIdEq(sellerId),
                productIdEq(productId))
            .fetch();
    }

    private BooleanExpression loginUserIdEq(Long loginUserId) {
        return (loginUserId == null) ? null : chatRoom.buyer.id.eq(loginUserId);
    }

    private BooleanExpression sellerIdEq(Long sellerId) {
        return (sellerId == null) ? null : chatRoom.seller.id.eq(sellerId);
    }

    private BooleanExpression productIdEq(Long productId) {
        return (productId == null) ? null : chatRoom.product.id.eq(productId);
    }
}

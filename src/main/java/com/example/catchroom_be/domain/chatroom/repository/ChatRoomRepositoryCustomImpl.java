package com.example.catchroom_be.domain.chatroom.repository;

import com.example.catchroom_be.domain.accommodation.entity.QAccommodation;
import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
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

    @Override
    public List<ChatRoom> findChatRoomList(Long buyerId, Long sellerId, Long productId) {
        return queryFactory
                .select(chatRoom).from(chatRoom)
                .where(
                        buyerIdEq(buyerId),
                        sellerIdEq(sellerId),
                        productIdEq(productId)
                )
                .fetch();
    }

    private BooleanExpression buyerIdEq(Long buyerId) {
        return (buyerId == null) ? null : chatRoom.buyer.id.eq(buyerId);
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

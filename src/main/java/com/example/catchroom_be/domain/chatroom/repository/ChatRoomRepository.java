package com.example.catchroom_be.domain.chatroom.repository;

import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    @Query("select c from ChatRoom c "
            + "left join fetch c.buyer "
            + "left join fetch c.seller "
            + "left join fetch c.product "
            + "left join fetch c.product.orderHistory.accommodation "
            + "where c.chatRoomNumber = :chatRoomNumber")
    Optional<ChatRoom> findByChatRoomNumber(@Param("chatRoomNumber") String chatRoomNumber);

    List<ChatRoom> findAllByBuyerIdOrSellerId(Long buyerId, Long SellerId);

    ChatRoom findByBuyerIdAndSellerIdAndProductId(Long buyerId, Long sellerId, Long productId);

}


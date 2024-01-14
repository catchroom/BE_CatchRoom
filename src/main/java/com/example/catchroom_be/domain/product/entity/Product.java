package com.example.catchroom_be.domain.product.entity;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.test_user.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;
    @ManyToOne
    @JoinColumn(name = "orderhisotry_id")
    private OrderHistory orderHistory;

    private String accommodationName;

}

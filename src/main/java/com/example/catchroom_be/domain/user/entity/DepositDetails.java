package com.example.catchroom_be.domain.user.entity;

import com.example.catchroom_be.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deposit_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DepositDetails extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "money")
    private int money;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

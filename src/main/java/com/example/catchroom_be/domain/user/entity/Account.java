package com.example.catchroom_be.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name ="account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "account_number",nullable = false)
    private String accountNumber;

    @Column (name = "account_owner",nullable = false)
    private String accountOwner;

    @Column (name= "balance",nullable = false)
    private String balance;

    @Column (name = "bank",nullable = false)
    private  String bankName;
}

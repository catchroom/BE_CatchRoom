package com.example.catchroom_be.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name ="user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "nick_name", nullable = false)
    private String  nickName;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone",nullable = false)
    private String phonenumber;

    @Column(name = "type")
    private String type;

    @Column(name = "bank")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private String balance;

    @Column(name = "account_owner")
    private String accountOwner;


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



}

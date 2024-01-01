package com.example.catchroom_be.Test.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "test_table")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users", nullable = false)
    private String users;


    public TestEntity() {
    }

    public TestEntity(String users) {
        this.users = users;
    }
    public String getusers() {
        return this.users;
    }
}

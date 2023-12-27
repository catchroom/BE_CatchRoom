package com.example.catchroom_be.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "test_table")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user", nullable = false)
    private String user;


    public TestEntity() {
    }

    public TestEntity(String user) {
        this.user = user;
    }
}

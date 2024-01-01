package com.example.catchroom_be.Test.repository;

import com.example.catchroom_be.Test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}

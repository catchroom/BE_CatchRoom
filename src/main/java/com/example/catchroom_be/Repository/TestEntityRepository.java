package com.example.catchroom_be.Repository;

import com.example.catchroom_be.Entity.TestEntity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}

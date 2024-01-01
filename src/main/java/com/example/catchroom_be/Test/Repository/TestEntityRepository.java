package com.example.catchroom_be.Test.Repository;

import com.example.catchroom_be.Test.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}

package com.example.catchroom_be.domain.test_user.repository;

import com.example.catchroom_be.domain.test_user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}

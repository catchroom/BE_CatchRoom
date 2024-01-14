package com.example.catchroom_be.domain.user.repository;

import com.example.catchroom_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByNickName(String nickName);
    Optional<User> findByEmail(String email);
    int countByEmail(String email);
    int countByNickName(String nickName);


}

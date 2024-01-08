package com.example.catchroom_be.domain.accommodation.repository;

import com.example.catchroom_be.domain.accommodation.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r "
            + "left join fetch r.accommodation "
            + "where r.id = :id")
    Optional<Room> findById(@Param("id") Long id);
}

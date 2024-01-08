package com.example.catchroom_be.domain.accommodation.repository;

import com.example.catchroom_be.domain.accommodation.entity.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select a from Accommodation a "
            + "left join fetch a.roomList "
            + "where a.id = :id")
    Optional<Accommodation> findById(@Param("id") Long id);

//    @Query("select a from Accommodation a "
//            + "left join fetch a.roomList")
//    List<Accommodation> findAll();
}

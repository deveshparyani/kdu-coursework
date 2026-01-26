package com.myproject.backendminiproject.repositories;

import com.myproject.backendminiproject.entities.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, UUID> {
    boolean existsByIdAndMembers_Username(UUID houseId, String username);

    @Query("""
    SELECT DISTINCT h
    FROM House h
    LEFT JOIN h.members m
    WHERE h.admin.username = :username
       OR m.username = :username
""")
    Page<House> findMyHouses(
            @Param("username") String username,
            Pageable pageable
    );

    Optional<Object> existsHouseById(UUID id);
}
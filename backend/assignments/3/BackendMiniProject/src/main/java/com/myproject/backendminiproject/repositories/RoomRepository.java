package com.myproject.backendminiproject.repositories;

import com.myproject.backendminiproject.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    Page<Room> findByHouse_Id(UUID houseId, Pageable pageable);
}
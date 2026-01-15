package com.myproject.hospitalstaffing.repositories;

import com.myproject.hospitalstaffing.entities.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftTypeRepo extends JpaRepository<ShiftType, UUID> {
}

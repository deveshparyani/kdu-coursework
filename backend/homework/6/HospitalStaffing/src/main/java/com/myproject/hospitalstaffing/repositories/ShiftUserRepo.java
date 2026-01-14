package com.myproject.hospitalstaffing.repositories;

import com.myproject.hospitalstaffing.entities.ShiftUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftUserRepo extends JpaRepository<ShiftUser, UUID> {
}

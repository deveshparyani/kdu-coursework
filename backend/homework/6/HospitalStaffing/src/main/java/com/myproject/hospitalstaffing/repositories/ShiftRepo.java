package com.myproject.hospitalstaffing.repositories;

import com.myproject.hospitalstaffing.entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftRepo extends JpaRepository<Shift, UUID> {
}

package com.myproject.hospitalstaffing.repositories;

import com.myproject.hospitalstaffing.entities.Shift;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ShiftRepo extends JpaRepository<Shift, UUID> {
    @Query("""
        SELECT s
        FROM Shift s
        WHERE s.startDate >= :startDate
          AND s.endDate   <= :endDate
        ORDER BY s.shiftName ASC
    """)
    List<Shift> findNewYearShifts(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}

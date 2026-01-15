package com.myproject.hospitalstaffing.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue
    private UUID id;

    private String shiftName;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn
    private ShiftType shiftType;
}

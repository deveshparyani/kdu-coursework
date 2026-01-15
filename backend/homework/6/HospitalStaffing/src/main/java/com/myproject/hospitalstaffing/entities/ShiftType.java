package com.myproject.hospitalstaffing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ShiftType {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "shiftType")
    private List<Shift> shifts;

}

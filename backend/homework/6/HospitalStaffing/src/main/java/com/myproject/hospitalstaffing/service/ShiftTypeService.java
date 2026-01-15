package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.ShiftType;
import com.myproject.hospitalstaffing.repositories.ShiftTypeRepo;
import org.springframework.stereotype.Service;

@Service
public class ShiftTypeService {

    private final ShiftTypeRepo repository;

    public ShiftTypeService(ShiftTypeRepo repository) {
        this.repository = repository;
    }

    public ShiftType save(ShiftType shiftType) {
        return repository.save(shiftType);
    }
}

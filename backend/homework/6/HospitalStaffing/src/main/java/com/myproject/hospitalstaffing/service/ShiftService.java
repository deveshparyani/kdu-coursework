package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.Shift;
import com.myproject.hospitalstaffing.repositories.ShiftRepo;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {

    private final ShiftRepo shiftRepository;

    public ShiftService(ShiftRepo shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }
}

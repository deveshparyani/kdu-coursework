package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.Shift;
import com.myproject.hospitalstaffing.repositories.ShiftRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepo shiftRepository;

    public ShiftService(ShiftRepo shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }

    public List<Shift> getTop3NewYearShifts() {

        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end   = LocalDate.of(2023, 1, 25);

        Pageable topThree = PageRequest.of(0, 3);

        return shiftRepository.findNewYearShifts(start, end, topThree);
    }
}

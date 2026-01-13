package com.myproject.smartcityhospital.controller;

import com.myproject.smartcityhospital.model.Shift;
import com.myproject.smartcityhospital.repositories.ShiftRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftRepository shiftRepository;

    public ShiftController(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @PostMapping
    public void createShift(@RequestBody Shift shift) {
        shiftRepository.saveShift(shift);
    }

    @GetMapping
    public List<Shift> getShiftsByTenant(
            @RequestParam UUID tenantId
    ) {
        return shiftRepository.findShiftsByTenant(tenantId);
    }
}

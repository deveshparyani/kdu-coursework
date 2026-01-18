package com.myproject.smartcityhospital.controller;

import com.myproject.smartcityhospital.model.ShiftType;
import com.myproject.smartcityhospital.repositories.ShiftRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shift-types")
public class ShiftTypeController {

    private final ShiftRepository shiftRepository;

    public ShiftTypeController(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @PostMapping
    public void createShiftType(
            @RequestBody ShiftType shiftType
    ) {
        shiftRepository.saveShiftType(shiftType);
    }
}
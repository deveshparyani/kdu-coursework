package com.myproject.hospitalstaffing.controller;

import com.myproject.hospitalstaffing.entities.Shift;
import com.myproject.hospitalstaffing.service.ShiftService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping
    public Shift createShift(@RequestBody Shift shift) {
        return shiftService.save(shift);
    }
}


package com.myproject.hospitalstaffing.controller;

import com.myproject.hospitalstaffing.entities.ShiftType;
import com.myproject.hospitalstaffing.service.ShiftTypeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shift-types")
public class ShiftTypeController {

    private final ShiftTypeService service;

    public ShiftTypeController(ShiftTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ShiftType create(@RequestBody ShiftType shiftType) {
        return service.save(shiftType);
    }
}


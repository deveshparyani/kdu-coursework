package com.myproject.hospitalstaffing.controller;

import com.myproject.hospitalstaffing.entities.ShiftUser;
import com.myproject.hospitalstaffing.service.ShiftUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shift-users")
public class ShiftUserController {

    private final ShiftUserService shiftUserService;

    public ShiftUserController(ShiftUserService shiftUserService) {
        this.shiftUserService = shiftUserService;
    }

    @PostMapping
    public ShiftUser assignUserToShift(@RequestBody ShiftUser shiftUser) {
        return shiftUserService.save(shiftUser);
    }
}

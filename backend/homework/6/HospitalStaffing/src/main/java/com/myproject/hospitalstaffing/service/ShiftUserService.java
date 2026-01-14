package com.myproject.hospitalstaffing.service;

import com.myproject.hospitalstaffing.entities.ShiftUser;
import com.myproject.hospitalstaffing.repositories.ShiftUserRepo;
import org.springframework.stereotype.Service;

@Service
public class ShiftUserService {

    private final ShiftUserRepo shiftUserRepo;

    public ShiftUserService(ShiftUserRepo shiftUserRepo) {
        this.shiftUserRepo = shiftUserRepo;
    }

    public ShiftUser save(ShiftUser shiftUser) {
        return shiftUserRepo.save(shiftUser);
    }
}

package com.myproject.smartcityhospital.service;

import com.myproject.smartcityhospital.model.Shift;
import com.myproject.smartcityhospital.model.ShiftType;
import com.myproject.smartcityhospital.model.User;
import com.myproject.smartcityhospital.repositories.ShiftRepository;
import com.myproject.smartcityhospital.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OnboardingService {

    private final UserRepository userRepo;
    private final ShiftRepository shiftRepo;

    public OnboardingService(
            UserRepository userRepo,
            ShiftRepository shiftRepo
    ) {
        this.userRepo = userRepo;
        this.shiftRepo = shiftRepo;
    }

    @Transactional
    public void onboardTenant(UUID tenantId) {

        // 1️⃣ Create ShiftType model
        UUID shiftTypeId = UUID.randomUUID();
        ShiftType shiftType = new ShiftType(
                shiftTypeId,
                "Morning",
                "Morning shift",
                true,
                tenantId
        );

        shiftRepo.saveShiftType(shiftType);

        // 2️⃣ Create Shift model
        Shift shift = new Shift(
                UUID.randomUUID(),
                shiftTypeId,
                tenantId
        );

        shiftRepo.saveShift(shift);

        // 3️⃣ INTENTIONAL FAILURE (Exercise 2)
        userRepo.save(
                new User(null, "doctor1", "Asia/Kolkata", tenantId)
        );
    }

}

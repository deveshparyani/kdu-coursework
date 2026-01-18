package com.myproject.smartcityhospital.controller;

import com.myproject.smartcityhospital.service.OnboardingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/onboard")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping("/{tenantId}")
    public void onboard(@PathVariable UUID tenantId) {
        onboardingService.onboardTenant(tenantId);
    }
}

package com.myproject.smartlocksecuritysystem.service;

import com.myproject.smartlocksecuritysystem.exception.HardwareFailureException;
import org.springframework.stereotype.Service;

@Service
public class SmartLockService {

    public void unlock(String user) {

        if (user == null || user.isEmpty()) {
            throw new HardwareFailureException("Empty input detected");
        }

        System.out.println("The door is now open for " + user);
    }

    public void checkBattery() {
        try {
            Thread.sleep(200); // simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Battery level is OK");
    }
}
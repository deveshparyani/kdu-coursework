package com.myproject.smartlocksecuritysystem;

import com.myproject.smartlocksecuritysystem.service.SmartLockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartLockSecuritySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLockSecuritySystemApplication.class, args);
    }

    @Bean
    CommandLineRunner run(SmartLockService service) {
        return args -> {

            service.checkBattery();

            service.unlock("Guest");    // allowed
            service.unlock("Unknown");  // blocked

            try{
                service.unlock("");
            } catch (Exception e) {
                // intentionally ignored for demo
            }
        };
    }
}

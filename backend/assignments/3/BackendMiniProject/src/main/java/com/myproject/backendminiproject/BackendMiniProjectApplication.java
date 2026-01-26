package com.myproject.backendminiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendMiniProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMiniProjectApplication.class, args);
    }

}

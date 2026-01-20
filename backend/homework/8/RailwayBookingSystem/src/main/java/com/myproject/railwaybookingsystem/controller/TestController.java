package com.myproject.railwaybookingsystem.controller;

import com.myproject.railwaybookingsystem.service.BookingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final BookingProducer producer;

    @PostMapping("/send-valid")
    public void valid() {
        producer.sendValid();
    }

    @PostMapping("/send-corrupt")
    public void corrupt() {
        producer.sendCorrupt();
    }
}

package com.myproject.railwaybookingsystem.controller;

import com.myproject.railwaybookingsystem.service.BookingService;
import com.myproject.railwaybookingsystem.service.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final PaymentProducer paymentProducer;

    @PostMapping("/book")
    public String book() {
        return bookingService.bookTicket();
    }

    // for success criteria (same payment pushed 3 times)
    @PostMapping("/pay")
    public void pay() {
        paymentProducer.sendSamePaymentThreeTimes();
    }
}
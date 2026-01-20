package com.myproject.railwaybookingsystem.meassage;

public record PaymentMessage(
        String transactionId,
        String bookingId,
        double amount
) {}

package com.myproject.railwaybookingsystem.meassage;


public record TicketBookedMessage(
        String bookingId,
        String seatNumber,
        String phoneNumber
) {}

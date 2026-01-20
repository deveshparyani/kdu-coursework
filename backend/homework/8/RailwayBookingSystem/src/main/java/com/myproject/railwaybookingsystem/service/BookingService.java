package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.InMemoryBroker;
import com.myproject.railwaybookingsystem.meassage.TicketBookedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final InMemoryBroker broker;

    public String bookTicket() {
        TicketBookedMessage message = new TicketBookedMessage(
                UUID.randomUUID().toString(),
                "S1",
                "9999999999"
        );

        broker.publishTicketBooked(message);

        return "Booking in Progress";
    }
}

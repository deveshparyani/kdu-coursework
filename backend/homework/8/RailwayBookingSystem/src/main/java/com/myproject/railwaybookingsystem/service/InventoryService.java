package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.InMemoryBroker;
import com.myproject.railwaybookingsystem.meassage.TicketBookedMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InMemoryBroker broker;

    @PostConstruct
    public void start() {
        BlockingQueue<TicketBookedMessage> queue =
                broker.registerTicketSubscriber();

        new Thread(() -> {
            while (true) {
                try {
                    TicketBookedMessage msg = queue.take();
                    System.out.println(
                            "Inventory Service: Seat "
                                    + msg.seatNumber()
                                    + " booked for " + msg.bookingId()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
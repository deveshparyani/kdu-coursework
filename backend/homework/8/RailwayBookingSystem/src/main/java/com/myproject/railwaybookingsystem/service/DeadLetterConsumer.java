package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.BookingQueues;
import com.myproject.railwaybookingsystem.meassage.BookingMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeadLetterConsumer {

    private final BookingQueues queues;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    BookingMessage msg = queues.deadLetterQueue.take();
                    System.out.println(
                            "DLQ RECEIVED → BookingId: "
                                    + msg.getBookingId()
                                    + ", retries: " + msg.getRetryCount()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.BookingQueues;
import com.myproject.railwaybookingsystem.meassage.BookingMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingConsumer {

    private static final int MAX_RETRIES = 3;

    private final BookingQueues queues;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    BookingMessage msg = queues.mainQueue.take();
                    process(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void process(BookingMessage msg) throws InterruptedException {

        try {
            if (msg.getAge() < 0) {
                throw new IllegalArgumentException("Invalid age");
            }

            System.out.println(
                    "Booking processed successfully: " + msg.getBookingId()
            );

        } catch (Exception ex) {

            msg.incrementRetry();

            System.out.println(
                    "Processing failed for "
                            + msg.getBookingId()
                            + " | retry = " + msg.getRetryCount()
            );

            if (msg.getRetryCount() >= MAX_RETRIES) {
                System.out.println(
                        "Moving booking " + msg.getBookingId()
                                + " to DLQ"
                );
                queues.deadLetterQueue.offer(msg);
            } else {
                // Retry delay
                Thread.sleep(1000);
                queues.mainQueue.offer(msg);
            }
        }
    }
}

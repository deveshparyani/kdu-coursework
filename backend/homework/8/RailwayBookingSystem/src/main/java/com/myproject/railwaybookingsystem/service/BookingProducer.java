package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.BookingQueues;
import com.myproject.railwaybookingsystem.meassage.BookingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingProducer {

    private final BookingQueues queues;

    public void sendValid() {
        queues.mainQueue.offer(
                new BookingMessage("BOOK-1", 25)
        );
    }

    public void sendCorrupt() {
        queues.mainQueue.offer(
                new BookingMessage("BOOK-ERROR", -5)
        );
    }
}

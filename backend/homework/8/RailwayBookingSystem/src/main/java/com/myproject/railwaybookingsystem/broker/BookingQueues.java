package com.myproject.railwaybookingsystem.broker;

import com.myproject.railwaybookingsystem.meassage.BookingMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class BookingQueues {

    public final BlockingQueue<BookingMessage> mainQueue =
            new LinkedBlockingQueue<>();

    public final BlockingQueue<BookingMessage> deadLetterQueue =
            new LinkedBlockingQueue<>();
}

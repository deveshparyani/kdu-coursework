package com.myproject.railwaybookingsystem.broker;

import com.myproject.railwaybookingsystem.meassage.PaymentMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class PaymentQueue {
    public final BlockingQueue<PaymentMessage> queue =
            new LinkedBlockingQueue<>();
}

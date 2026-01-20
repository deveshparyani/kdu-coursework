package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.PaymentQueue;
import com.myproject.railwaybookingsystem.meassage.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final PaymentQueue paymentQueue;

    public void sendSamePaymentThreeTimes() {
        PaymentMessage msg = new PaymentMessage(
                "TXN-123",
                "BOOK-1",
                500
        );

        paymentQueue.queue.offer(msg);
        paymentQueue.queue.offer(msg);
        paymentQueue.queue.offer(msg);
    }
}

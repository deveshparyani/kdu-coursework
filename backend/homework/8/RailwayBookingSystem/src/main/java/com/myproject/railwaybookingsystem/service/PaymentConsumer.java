package com.myproject.railwaybookingsystem.service;

import com.myproject.railwaybookingsystem.broker.PaymentQueue;
import com.myproject.railwaybookingsystem.meassage.PaymentMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentQueue paymentQueue;

    private final Set<String> processedTransactions =
            ConcurrentHashMap.newKeySet();

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    PaymentMessage msg = paymentQueue.queue.take();

                    if (processedTransactions.contains(msg.transactionId())) {
                        System.out.println(
                                "Transaction " + msg.transactionId() + " already processed. Ignoring duplicate."
                        );
                        continue;
                    }

                    processedTransactions.add(msg.transactionId());

                    System.out.println(
                            "Money Deducted: " + msg.amount() + " for booking " + msg.bookingId()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

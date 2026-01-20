package com.myproject.railwaybookingsystem.broker;

import com.myproject.railwaybookingsystem.meassage.TicketBookedMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class InMemoryBroker {

    private final List<BlockingQueue<TicketBookedMessage>> ticketSubscribers =
            new CopyOnWriteArrayList<>();

    public BlockingQueue<TicketBookedMessage> registerTicketSubscriber() {
        BlockingQueue<TicketBookedMessage> queue = new LinkedBlockingQueue<>();
        ticketSubscribers.add(queue);
        return queue;
    }

    public void publishTicketBooked(TicketBookedMessage message) {
        for (BlockingQueue<TicketBookedMessage> q : ticketSubscribers) {
            q.offer(message); // fan-out copy
        }
    }
}


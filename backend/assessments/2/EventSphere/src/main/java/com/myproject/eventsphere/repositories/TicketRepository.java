package com.myproject.eventsphere.repositories;

import com.myproject.eventsphere.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
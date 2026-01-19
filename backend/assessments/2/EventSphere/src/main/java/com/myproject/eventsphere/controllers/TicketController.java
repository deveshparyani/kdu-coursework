package com.myproject.eventsphere.controllers;

import com.myproject.eventsphere.entities.Booking;
import com.myproject.eventsphere.entities.Ticket;
import com.myproject.eventsphere.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    public final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping("/{eventId}/reserve")
    public ResponseEntity<Ticket> createReservation(@PathVariable UUID eventId,
                                                    Authentication authentication){
        Ticket reservedTicket =  ticketService.createReservation(eventId, authentication.getName());

        return ResponseEntity.ok(reservedTicket);
    }

    @DeleteMapping("/{ticketId}/reservation")
    public ResponseEntity<?> deleteReservation(@PathVariable UUID ticketId){
        ticketService.deleteReservation(ticketId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{ticketId}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable UUID ticketId){
        Booking booking = ticketService.confirmBooking(ticketId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{bookingId}/confirmation")
    public ResponseEntity<?> deleteBooking(@PathVariable UUID bookingId){
        ticketService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}

package com.myproject.eventsphere.service;

import com.myproject.eventsphere.entities.Booking;
import com.myproject.eventsphere.entities.Event;
import com.myproject.eventsphere.entities.Ticket;
import com.myproject.eventsphere.entities.User;
import com.myproject.eventsphere.repositories.BookingRepository;
import com.myproject.eventsphere.repositories.EventRepository;
import com.myproject.eventsphere.repositories.TicketRepository;
import com.myproject.eventsphere.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {
    public final TicketRepository ticketRepository;
    public final EventRepository eventRepository;
    public final UserRepository userRepository;
    public final BookingRepository bookingRepository;

    public TicketService(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository,
                         BookingRepository bookingRepository){
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public Ticket createReservation(UUID eventId, String username){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(event.getTicketCount() <= 0){
            throw new RuntimeException("No Tickets Left");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket(event,user);

        ticket.setConfirmation(false);

        event.setTicketCount(event.getTicketCount() - 1);

        eventRepository.save(event);
        return  ticketRepository.save(ticket);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public void deleteReservation(UUID ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("No such Ticket exists"));

        Event event = ticket.getEvent();
        event.setTicketCount(event.getTicketCount() + 1);

        User user = ticket.getUser();

        event.getTickets().remove(ticket);
        user.getTickets().remove(ticket);

        eventRepository.save(event);
        userRepository.save(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public Booking confirmBooking(UUID ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("No Ticket Found"));
        ticket.setConfirmation(true);

        Booking booking = new Booking(ticket);

        ticketRepository.save(ticket);

        return bookingRepository.save(booking);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Transactional
    public void deleteBooking(UUID bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("No booking found"));

        Ticket ticket = booking.getTicket();

        Event event = ticket.getEvent();
        event.setTicketCount(event.getTicketCount() + 1);

        User user = ticket.getUser();

        user.getTickets().remove(ticket);
        event.getTickets().remove(ticket);

        bookingRepository.deleteById(bookingId);

    }
}

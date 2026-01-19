package com.myproject.eventsphere.repositories;

import com.myproject.eventsphere.entities.Booking;
import com.myproject.eventsphere.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
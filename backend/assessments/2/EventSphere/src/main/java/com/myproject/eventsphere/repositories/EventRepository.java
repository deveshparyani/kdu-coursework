package com.myproject.eventsphere.repositories;

import com.myproject.eventsphere.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
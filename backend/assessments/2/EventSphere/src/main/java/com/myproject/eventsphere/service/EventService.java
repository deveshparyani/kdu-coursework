package com.myproject.eventsphere.service;


import com.myproject.eventsphere.dto.CreateEvent;
import com.myproject.eventsphere.entities.Event;
import com.myproject.eventsphere.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Event addEvent(Event event){
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Event updateEvent(UUID eventId, String name, String date, String location){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        if(name != null && !name.isBlank()){
            event.setName(name);
        }

        if(date != null && !date.isBlank()){
            event.setDate(date);
        }

        if(location != null && !location.isBlank()){
            event.setDate(date);
        }

        return  eventRepository.save(event);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Page<Event> getEvents(Pageable pageable){
        return eventRepository.findAll(pageable);
    }
}

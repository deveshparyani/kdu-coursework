package com.myproject.eventsphere.controllers;

import com.myproject.eventsphere.dto.CreateEvent;
import com.myproject.eventsphere.entities.Event;
import com.myproject.eventsphere.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEvent createEvent){
        Event event = new Event(createEvent.getName(), createEvent.getDate(), createEvent.getLocation(), createEvent.getTicketCount());

        return ResponseEntity.ok(eventService.addEvent(event));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id,
                                             @RequestParam(defaultValue = "") String name,
                                             @RequestParam(defaultValue = "") String date,
                                             @RequestParam(defaultValue = "") String location){

        Event updatedEvent = eventService.updateEvent(id, name, date, location);

        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "true") boolean ascending){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return eventService.getEvents(pageable);
    }
}

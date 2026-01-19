package com.myproject.eventsphere.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String date;

    @Column(nullable = false)
    private String location;

    @PositiveOrZero(message = "Ticket count must be greater than zero")
    private Long ticketCount;

    @OneToMany(mappedBy = "event", orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> tickets;

    public Event(String name, String date, String location, Long ticketCount){
        this.name = name;
        this.date = date;
        this.location = location;
        this.ticketCount = ticketCount;
    }
}

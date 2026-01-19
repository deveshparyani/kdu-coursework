package com.myproject.eventsphere.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class CreateEvent {
    @Column(nullable = false)
    private String name;

    private String date;

    @Column(nullable = false)
    private String location;

    @PositiveOrZero(message = "Ticket count must be greater than zero")
    private Long ticketCount;
}

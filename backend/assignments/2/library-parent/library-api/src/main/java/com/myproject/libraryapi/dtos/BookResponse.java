package com.myproject.libraryapi.dtos;

import com.myproject.libraryapi.dtos.enums.BookStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO representing a book.
 */
@Data
@Builder
@Value
public class BookResponse {

    private UUID id;
    private String title;
    private BookStatus status;
    private Instant createdAt;

    // getters & setters
}
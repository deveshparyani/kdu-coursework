package com.myproject.libraryapi.dtos;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO representing a book loan.
 */
@Value
@Builder
public class LoanResponse {

    private UUID id;
    private UUID bookId;
    private UUID borrowerId;
    private Instant borrowedAt;
    private Instant returnedAt;

    // getters & setters
}
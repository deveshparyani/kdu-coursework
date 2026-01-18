package com.myproject.libraryapi.dtos;

import lombok.Data;


import java.time.Instant;
import java.util.List;

/**
 * Standard error response returned by all APIs.
 */
@Data
public class ErrorResponse {

    private Instant timestamp;
    private String path;
    private String errorCode;
    private String message;
    private List<FieldError> details;
    private String correlationId;

    // getters & setters
}
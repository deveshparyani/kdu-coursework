package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timeStamp;
}

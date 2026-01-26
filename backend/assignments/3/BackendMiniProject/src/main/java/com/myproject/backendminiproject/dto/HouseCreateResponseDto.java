package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class HouseCreateResponseDto {
    private UUID id;
    private String address;
    private String description;
    private UUID adminId;
    private String adminUsername;
    private int memberCount;
    private String createdAt;
}

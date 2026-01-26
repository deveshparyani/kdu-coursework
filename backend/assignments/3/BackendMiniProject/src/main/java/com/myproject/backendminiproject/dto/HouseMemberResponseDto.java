package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class HouseMemberResponseDto {
    private UUID userId;
    private String username;
    private String role;
}

package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class UpdateHouseResponseDto {
    private UUID houseId;
    private String address;
    private String description;
    private String adminUsername;
}

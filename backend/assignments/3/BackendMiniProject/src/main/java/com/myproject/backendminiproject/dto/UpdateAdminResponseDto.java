package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UpdateAdminResponseDto {
    private UUID houseId;
    private UUID adminId;
    private String adminName;
}

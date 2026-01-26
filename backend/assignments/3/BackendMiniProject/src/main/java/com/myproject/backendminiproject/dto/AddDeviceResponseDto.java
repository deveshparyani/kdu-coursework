package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class AddDeviceResponseDto {
    private UUID deviceId;
    private String kickstonId;
    private String deviceUsername;
    private UUID houseId;
}

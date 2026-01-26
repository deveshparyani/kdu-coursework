package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AddDeviceRequestDto {
    private String kickstonId;
    private String deviceUsername;
    private String devicePassword;
    private UUID houseId;
}

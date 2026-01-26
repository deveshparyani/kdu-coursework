package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AssignDeviceRequestDto {
    private UUID deviceId;
    private UUID houseId;
    private UUID roomId;
}

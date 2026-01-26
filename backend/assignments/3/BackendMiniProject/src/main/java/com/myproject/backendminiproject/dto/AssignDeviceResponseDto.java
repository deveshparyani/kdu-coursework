package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class AssignDeviceResponseDto {
    private UUID deviceId;
    private String deviceUsername;
    private UUID roomId;
    private String roomName;
}

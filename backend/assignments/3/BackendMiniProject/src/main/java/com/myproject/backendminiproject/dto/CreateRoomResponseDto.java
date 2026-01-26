package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CreateRoomResponseDto {
    private UUID roomId;
    private String roomName;
    private UUID houseId;
    private LocalDateTime createdAt;
}

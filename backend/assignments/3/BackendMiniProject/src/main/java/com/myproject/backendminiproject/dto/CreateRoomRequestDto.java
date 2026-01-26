package com.myproject.backendminiproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateRoomRequestDto {
    private UUID houseId;
    private String roomName;
}

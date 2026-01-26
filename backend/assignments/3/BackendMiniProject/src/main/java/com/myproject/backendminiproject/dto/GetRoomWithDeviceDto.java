package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class GetRoomWithDeviceDto {
    private UUID roomId;
    private String roomName;

    private List<GetDeviceDto> devices;
}

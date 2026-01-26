package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class GetHouseRoomsWithDevicesResponseDto {
    private UUID houseId;
    private String houseAddress;

    private List<GetRoomWithDeviceDto> rooms;
}

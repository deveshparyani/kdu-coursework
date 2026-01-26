package com.myproject.backendminiproject.dto;

import com.myproject.backendminiproject.entities.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class GetRoomReponseDto {
    private UUID roomId;
    private String roomName;
    private UUID houseId;
    private LocalDateTime createdDate;

    public static GetRoomReponseDto from(Room room){
        return GetRoomReponseDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .houseId(room.getHouse().getId())
                .createdDate(room.getCreatedDate())
                .build();
    }
}

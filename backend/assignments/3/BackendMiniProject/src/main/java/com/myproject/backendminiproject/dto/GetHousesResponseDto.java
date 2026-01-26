package com.myproject.backendminiproject.dto;

import com.myproject.backendminiproject.entities.House;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@Setter
public class GetHousesResponseDto {
    private UUID id;
    private String address;
    private String description;

    private UUID adminId;
    private String adminUsername;

    private int memberCount;


    public static GetHousesResponseDto from(House house){
        return GetHousesResponseDto.builder()
                .id(house.getId())
                .address(house.getAddress())
                .description(house.getDescription())
                .adminId(house.getAdmin().getId())
                .adminUsername(house.getAdmin().getUsername())
                .memberCount(house.getMembers().size())
                .build();
    }
}



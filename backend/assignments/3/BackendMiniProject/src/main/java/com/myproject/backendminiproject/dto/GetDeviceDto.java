package com.myproject.backendminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class GetDeviceDto {
    private UUID id;
    private String kickstonId;
    private String deviceUsername;
}

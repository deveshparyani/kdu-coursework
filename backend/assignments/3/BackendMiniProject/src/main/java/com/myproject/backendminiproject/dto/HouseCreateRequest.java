package com.myproject.backendminiproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseCreateRequest {
    private String address;
    private String description;
}

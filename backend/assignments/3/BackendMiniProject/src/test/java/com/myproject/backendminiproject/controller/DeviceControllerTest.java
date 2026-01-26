package com.myproject.backendminiproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.backendminiproject.dto.*;
import com.myproject.backendminiproject.security.JwtService;
import com.myproject.backendminiproject.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DeviceService deviceService;

    @MockitoBean
    private JwtService jwtService;

    // 🔥 THIS is the critical fix
    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
            jpaMetamodelMappingContext;

    @Test
    void getRoomsWithDevices_returns200() throws Exception {
        UUID houseId = UUID.randomUUID();

        GetHouseRoomsWithDevicesResponseDto response =
                GetHouseRoomsWithDevicesResponseDto.builder()
                        .houseId(houseId)
                        .houseAddress("Jaipur")
                        .rooms(List.of())
                        .build();

        when(deviceService.getRoomsWithDevices(houseId))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/devices/{houseId}", houseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.houseAddress").value("Jaipur"));
    }
}



package com.myproject.backendminiproject.controller;

import com.myproject.backendminiproject.dto.*;
import com.myproject.backendminiproject.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.myproject.backendminiproject.constants.ApiConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<AddDeviceResponseDto> addDevice(
            @RequestBody AddDeviceRequestDto addDeviceRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(deviceService.addDevice(addDeviceRequestDto,userDetails.getUsername()));
    }

    @PutMapping
    public  ResponseEntity<AssignDeviceResponseDto> assignDevice(
            @RequestBody AssignDeviceRequestDto assignDeviceRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(deviceService.assignDevice(assignDeviceRequestDto, userDetails.getUsername()));
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<GetHouseRoomsWithDevicesResponseDto> getRoomsWithDevices(
            @PathVariable UUID houseId
    ) {
        GetHouseRoomsWithDevicesResponseDto response =
                deviceService.getRoomsWithDevices(houseId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> unassignDevice(
            @PathVariable UUID deviceId,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        deviceService.unassignDevice(deviceId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}

package com.myproject.backendminiproject.service;

import com.myproject.backendminiproject.dto.AssignDeviceRequestDto;
import com.myproject.backendminiproject.dto.AssignDeviceResponseDto;
import com.myproject.backendminiproject.entities.Device;
import com.myproject.backendminiproject.entities.House;
import com.myproject.backendminiproject.entities.Room;
import com.myproject.backendminiproject.exception.BadRequestException;
import com.myproject.backendminiproject.repositories.DeviceRepository;
import com.myproject.backendminiproject.repositories.HouseRepository;
import com.myproject.backendminiproject.repositories.RoomRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private DeviceService deviceService;

    private House house;
    private Room fromRoom;
    private Room toRoom;
    private Device device;

    private UUID houseId;
    private UUID fromRoomId;
    private UUID toRoomId;
    private UUID deviceId;

    @BeforeEach
    void setup() {
        houseId = UUID.randomUUID();
        fromRoomId = UUID.randomUUID();
        toRoomId = UUID.randomUUID();
        deviceId = UUID.randomUUID();

        // House
        house = new House();
        house.setId(houseId);

        // admin
        var admin = new com.myproject.backendminiproject.entities.User();
        admin.setUsername("admin");
        house.setAdmin(admin);

        // From room
        fromRoom = new Room();
        fromRoom.setId(fromRoomId);
        fromRoom.setRoomName("Living Room");
        fromRoom.setHouse(house);
        fromRoom.setDevices(new ArrayList<>());

        // To room
        toRoom = new Room();
        toRoom.setId(toRoomId);
        toRoom.setRoomName("Bedroom");
        toRoom.setHouse(house);
        toRoom.setDevices(new ArrayList<>());

        // Device
        device = new Device();
        device.setId(deviceId);
        device.setDeviceUsername("dev1");
        device.setHouse(house);
        device.setRoom(fromRoom);

        fromRoom.getDevices().add(device);
    }

    // ✅ SUCCESS CASE
    @Test
    void assignDevice_success_movesDeviceToAnotherRoom() {

        AssignDeviceRequestDto request = AssignDeviceRequestDto.builder()
                .houseId(houseId)
                .roomId(toRoomId)
                .deviceId(deviceId)
                .build();

        when(houseRepository.findById(houseId))
                .thenReturn(Optional.of(house));
        when(deviceRepository.findById(deviceId))
                .thenReturn(Optional.of(device));
        when(roomRepository.findById(toRoomId))
                .thenReturn(Optional.of(toRoom));

        AssignDeviceResponseDto response =
                deviceService.assignDevice(request, "admin");

        // 🔁 moved
        assertFalse(fromRoom.getDevices().contains(device));
        assertTrue(toRoom.getDevices().contains(device));
        assertEquals(toRoom, device.getRoom());

        // response check
        assertEquals(deviceId, response.getDeviceId());
        assertEquals("dev1", response.getDeviceUsername());
        assertEquals(toRoomId, response.getRoomId());

        verify(houseRepository).findById(houseId);
        verify(deviceRepository).findById(deviceId);
        verify(roomRepository).findById(toRoomId);
    }

    // ❌ NON-ADMIN
    @Test
    void assignDevice_notAdmin_throwsAccessDenied() {

        AssignDeviceRequestDto request = AssignDeviceRequestDto.builder()
                .houseId(houseId)
                .roomId(toRoomId)
                .deviceId(deviceId)
                .build();

        when(houseRepository.findById(houseId))
                .thenReturn(Optional.of(house));

        assertThrows(AccessDeniedException.class, () ->
                deviceService.assignDevice(request, "not-admin")
        );

        verify(deviceRepository, never()).findById(any());
    }

    // ❌ ROOM NOT IN HOUSE
    @Test
    void assignDevice_roomNotInHouse_throwsBadRequest() {

        // create a DIFFERENT house with an ID
        House otherHouse = new House();
        otherHouse.setId(UUID.randomUUID());

        Room otherHouseRoom = new Room();
        otherHouseRoom.setId(toRoomId);
        otherHouseRoom.setHouse(otherHouse);

        AssignDeviceRequestDto request = AssignDeviceRequestDto.builder()
                .houseId(houseId)
                .roomId(toRoomId)
                .deviceId(deviceId)
                .build();

        when(houseRepository.findById(houseId))
                .thenReturn(Optional.of(house));
        when(deviceRepository.findById(deviceId))
                .thenReturn(Optional.of(device));
        when(roomRepository.findById(toRoomId))
                .thenReturn(Optional.of(otherHouseRoom));

        assertThrows(BadRequestException.class, () ->
                deviceService.assignDevice(request, "admin")
        );
    }

}

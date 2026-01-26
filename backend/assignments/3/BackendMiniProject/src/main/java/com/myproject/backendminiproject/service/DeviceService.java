package com.myproject.backendminiproject.service;

import com.myproject.backendminiproject.dto.*;
import com.myproject.backendminiproject.entities.Device;
import com.myproject.backendminiproject.entities.House;
import com.myproject.backendminiproject.entities.Room;
import com.myproject.backendminiproject.exception.BadRequestException;
import com.myproject.backendminiproject.exception.ResourceNotFoundException;
import com.myproject.backendminiproject.repositories.DeviceRepository;
import com.myproject.backendminiproject.repositories.HouseRepository;
import com.myproject.backendminiproject.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.myproject.backendminiproject.constants.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final HouseRepository houseRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoomRepository roomRepository;

    @Transactional
    public AddDeviceResponseDto addDevice(AddDeviceRequestDto addDeviceRequestDto, String username){
        House house = houseRepository.findById(addDeviceRequestDto.getHouseId())
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        Device device = deviceRepository
                .findByKickstonIdAndDeviceUsername(addDeviceRequestDto.getKickstonId(), addDeviceRequestDto.getDeviceUsername())
                .orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        if (!passwordEncoder.matches(addDeviceRequestDto.getDevicePassword(), device.getDevicePassword())) {
            throw new BadRequestException(INVALID_DEVICE_CREDENTIALS);
        }

        if(device.getHouse() != null){
            throw new BadRequestException(DEVICE_ALREADY_ASSIGNED);
        }

        device.setHouse(house);

        return AddDeviceResponseDto.builder()
                .deviceId(device.getId())
                .kickstonId(device.getKickstonId())
                .deviceUsername(device.getDeviceUsername())
                .houseId(house.getId())
                .build();
    }

    @Transactional
    public AssignDeviceResponseDto assignDevice(AssignDeviceRequestDto assignDeviceRequestDto, String username){
        House house = houseRepository.findById(assignDeviceRequestDto.getHouseId())
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        Device device = deviceRepository.findById(assignDeviceRequestDto.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        Room room = roomRepository.findById(assignDeviceRequestDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException(ROOM_NOT_FOUND));

        if(!room.getHouse().getId().equals(assignDeviceRequestDto.getHouseId())){
            throw new BadRequestException(ROOM_NOT_IN_HOUSE);
        }

        if(device.getRoom() != null){
            device.getRoom().getDevices().remove(device);
        }

        room.getDevices().add(device);
        device.setRoom(room);

        device.setModifiedDate(LocalDateTime.now());

        return AssignDeviceResponseDto.builder()
                .deviceId(device.getId())
                .deviceUsername(device.getDeviceUsername())
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .build();
    }

    @Transactional(readOnly = true)
    public GetHouseRoomsWithDevicesResponseDto getRoomsWithDevices(UUID houseId) {

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        List<GetRoomWithDeviceDto> roomDtos =
                house.getRooms().stream()
                        .map(room -> GetRoomWithDeviceDto.builder()
                                .roomId(room.getId())
                                .roomName(room.getRoomName())
                                .devices(
                                        room.getDevices().stream()
                                                .map(device -> GetDeviceDto.builder()
                                                        .id(device.getId())
                                                        .kickstonId(device.getKickstonId())
                                                        .deviceUsername(device.getDeviceUsername())
                                                        .build()
                                                )
                                                .toList()
                                )
                                .build()
                        )
                        .toList();

        return GetHouseRoomsWithDevicesResponseDto.builder()
                .houseId(house.getId())
                .houseAddress(house.getAddress())
                .rooms(roomDtos)
                .build();
    }

    @Transactional
    public void unassignDevice(UUID deviceId, String username) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        House house = device.getHouse();

        if(house == null){
            throw new ResourceNotFoundException(DEVICE_NOT_ASSIGNED);
        }

        if (!house.getAdmin().getUsername().equals(username)) {
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        device.setModifiedDate(LocalDateTime.now());

        device.setRoom(null);
        device.setHouse(null);
    }

}

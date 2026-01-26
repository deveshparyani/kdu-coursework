package com.myproject.backendminiproject.service;


import com.myproject.backendminiproject.dto.CreateRoomRequestDto;
import com.myproject.backendminiproject.dto.CreateRoomResponseDto;
import com.myproject.backendminiproject.dto.GetRoomReponseDto;
import com.myproject.backendminiproject.entities.Device;
import com.myproject.backendminiproject.entities.House;
import com.myproject.backendminiproject.entities.Room;
import com.myproject.backendminiproject.exception.ResourceNotFoundException;
import com.myproject.backendminiproject.repositories.HouseRepository;
import com.myproject.backendminiproject.repositories.RoomRepository;
import com.myproject.backendminiproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.myproject.backendminiproject.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto, String username){
        House house  = houseRepository.findById(createRoomRequestDto.getHouseId())
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        Room room = new Room();
        room.setHouse(house);

        room.setRoomName(createRoomRequestDto.getRoomName());

        house.getRooms().add(room);

        roomRepository.save(room);

        return CreateRoomResponseDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .houseId(house.getId())
                .createdAt(room.getCreatedDate())
                .build();
    }

    public Page<GetRoomReponseDto> getRooms(UUID houseId, Pageable pageable){
        if (!houseRepository.existsById(houseId)) {
            throw new ResourceNotFoundException(HOUSE_NOT_FOUND);
        }

        Page<Room> roomPage = roomRepository.findByHouse_Id(houseId, pageable);

        return roomPage.map(GetRoomReponseDto::from);
    }

    @Transactional
    public void deleteRoom(UUID roomId, String username){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(ROOM_NOT_FOUND));

        House house = room.getHouse();

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        for(Device device : room.getDevices()){
            device.setRoom(null);
        }

        room.setModifiedDate(LocalDateTime.now());

        room.markDeleted();
    }

}

package com.myproject.backendminiproject.service;

import com.myproject.backendminiproject.dto.*;
import com.myproject.backendminiproject.entities.Device;
import com.myproject.backendminiproject.entities.House;
import com.myproject.backendminiproject.entities.Room;
import com.myproject.backendminiproject.entities.User;
import com.myproject.backendminiproject.exception.ResourceNotFoundException;
import com.myproject.backendminiproject.repositories.HouseRepository;
import com.myproject.backendminiproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.myproject.backendminiproject.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public HouseCreateResponseDto createHouse(String username, HouseCreateRequest houseCreateRequest){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        House house = House.builder()
                .admin(user)
                .address(houseCreateRequest.getAddress())
                .description(houseCreateRequest.getDescription())
                .build();

        house.getMembers().add(user);

        user.getHouses().add(house);
        user.getAdminHouses().add(house);

        House createdHouse = houseRepository.save(house);

        return HouseCreateResponseDto.builder()
                .id(createdHouse.getId())
                .address(createdHouse.getAddress())
                .description(createdHouse.getDescription())
                .adminId(user.getId())
                .adminUsername(user.getUsername())
                .memberCount(createdHouse.getMembers().toArray().length)
                .createdAt(createdHouse.getCreatedDate().toString())
                .build();
    }

    public Page<GetHousesResponseDto> getMyHouses(String username, Pageable pageable){
        Page<House> housePage = houseRepository.findMyHouses(username, pageable);

        return housePage.map(GetHousesResponseDto::from);
    }

    @Transactional
    public HouseMemberResponseDto addUser(String username, UUID houseId, String admin){
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(admin)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        house.getMembers().add(user);
        user.getHouses().add(house);

        house.setModifiedDate(LocalDateTime.now());

        return HouseMemberResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role("MEMBER")
                .build();
    }

    @Transactional
    public UpdateHouseResponseDto updateHouseDetails(
            UpdateHouseRequestDto updateHouseRequestDto,
            String username,
            UUID houseId
    ){
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        if(updateHouseRequestDto.getAddress() != null){
            house.setAddress(updateHouseRequestDto.getAddress());
            house.setModifiedDate(LocalDateTime.now());
        }

        if(updateHouseRequestDto.getDescription() != null){
            house.setDescription(updateHouseRequestDto.getDescription());
            house.setModifiedDate(LocalDateTime.now());
        }

        return UpdateHouseResponseDto.builder()
                .houseId(house.getId())
                .address(house.getAddress())
                .description(house.getDescription())
                .adminUsername(house.getAdmin().getUsername())
                .build();
    }

    @Transactional
    public UpdateAdminResponseDto updateAdmin(
        UpdateAdminRequestDto updateAdminRequestDto,
        String username,
        UUID houseId
    ){
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdmin().getUsername().equals(username)){
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        User newAdmin = userRepository.findByUsername(updateAdminRequestDto.getNewAdminUsername())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if (!houseRepository.existsByIdAndMembers_Username(houseId, updateAdminRequestDto.getNewAdminUsername())) {
            throw new AccessDeniedException(USER_NOT_MEMBER_OF_HOUSE);
        }

        house.setAdmin(newAdmin);
        house.setModifiedDate(LocalDateTime.now());

        return UpdateAdminResponseDto.builder()
                .houseId(houseId)
                .adminId(newAdmin.getId())
                .adminName(newAdmin.getUsername())
                .build();
    }

    @Transactional
    public void deleteHouse(UUID houseId, String username){
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException(HOUSE_NOT_FOUND));

        if (!house.getAdmin().getUsername().equals(username)) {
            throw new AccessDeniedException(ONLY_ADMIN_ALLOWED);
        }

        for (Room room : house.getRooms()) {
            for (Device device : room.getDevices()) {
                device.setRoom(null);
                device.setHouse(null);
            }
        }

        for (Room room : house.getRooms()) {
            room.markDeleted();
        }

        house.setModifiedDate(LocalDateTime.now());
        house.markDeleted();
    }

}

package com.myproject.backendminiproject.controller;


import com.myproject.backendminiproject.dto.*;
import com.myproject.backendminiproject.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.myproject.backendminiproject.constants.ApiConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<HouseCreateResponseDto> createHouse(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody HouseCreateRequest houseCreateRequest
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(houseService.createHouse(userDetails.getUsername(), houseCreateRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<GetHousesResponseDto>> getHouses(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(houseService.getMyHouses(userDetails.getUsername(), pageable));
    }

    @PostMapping("/{houseId}/members")
    public ResponseEntity<HouseMemberResponseDto> addUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID houseId,
            @RequestBody HouseMemberRequestDto houseMemberRequestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(houseService.addUser(
                        houseMemberRequestDto.getUsername(),
                        houseId,
                        userDetails.getUsername()
                ));
    }

    @PatchMapping("/{houseId}/details")
    public ResponseEntity<UpdateHouseResponseDto> updateHouseDetails(
            @RequestBody UpdateHouseRequestDto updateHouseRequestDto,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID houseId
    ){
        return ResponseEntity.ok(houseService.updateHouseDetails(
                updateHouseRequestDto,
                userDetails.getUsername(),
                houseId
        ));
    }


    @PatchMapping("/{houseId}/admin")
    public ResponseEntity<UpdateAdminResponseDto> updateAdmin(
            @RequestBody UpdateAdminRequestDto updateAdminRequestDto,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID houseId
    ){
        return ResponseEntity.ok(houseService.updateAdmin(
           updateAdminRequestDto,
                userDetails.getUsername(),
                houseId
        ));
    }

    @DeleteMapping("/{houseId}/delete")
    public ResponseEntity<Void> deleteHouse(
            @PathVariable UUID houseId,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        houseService.deleteHouse(houseId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}

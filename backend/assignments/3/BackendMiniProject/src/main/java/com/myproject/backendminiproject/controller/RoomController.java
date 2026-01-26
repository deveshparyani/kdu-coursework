package com.myproject.backendminiproject.controller;


import com.myproject.backendminiproject.dto.CreateRoomRequestDto;
import com.myproject.backendminiproject.dto.CreateRoomResponseDto;
import com.myproject.backendminiproject.dto.GetRoomReponseDto;
import com.myproject.backendminiproject.service.RoomService;
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

import java.util.List;
import java.util.UUID;

import static com.myproject.backendminiproject.constants.ApiConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<CreateRoomResponseDto> createRoom(
            @RequestBody CreateRoomRequestDto createRoomRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.createRoom(createRoomRequestDto, userDetails.getUsername()));
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<Page<GetRoomReponseDto>> getRooms(
            @PathVariable UUID houseId,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomService.getRooms(houseId, pageable));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable UUID roomId,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        roomService.deleteRoom(roomId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}

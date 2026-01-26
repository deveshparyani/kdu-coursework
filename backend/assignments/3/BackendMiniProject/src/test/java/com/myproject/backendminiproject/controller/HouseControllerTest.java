package com.myproject.backendminiproject.controller;

import com.myproject.backendminiproject.dto.GetHousesResponseDto;
import com.myproject.backendminiproject.service.HouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class HouseControllerTest {

    @Mock
    private HouseService houseService;

    @InjectMocks
    private HouseController houseController;

    @Test
    public void getMyHouses_returnsPagedHouses() {
        // given
        String username = "user";

        UserDetails userDetails =
                User.withUsername(username).password("pass").roles("USER").build();

        GetHousesResponseDto dto1 = GetHousesResponseDto.builder()
                .id(UUID.randomUUID())
                .address("Jaipur")
                .adminUsername("admin")
                .memberCount(2)
                .build();

        GetHousesResponseDto dto2 = GetHousesResponseDto.builder()
                .id(UUID.randomUUID())
                .address("Delhi")
                .adminUsername("admin2")
                .memberCount(3)
                .build();

        int pageNo = 0;
        int pageSize = 10;

        Pageable expectedPageable = PageRequest.of(pageNo, pageSize);

        Page<GetHousesResponseDto> expectedPage =
                new PageImpl<>(List.of(dto1, dto2), expectedPageable, 2);

        when(houseService.getMyHouses(eq(username), any(Pageable.class)))
                .thenReturn(expectedPage);

        // when
        ResponseEntity<Page<GetHousesResponseDto>> response =
                houseController.getHouses(userDetails, expectedPageable);

        Page<GetHousesResponseDto> result = response.getBody();

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(pageNo, result.getNumber());
        assertEquals(pageSize, result.getSize());

        ArgumentCaptor<Pageable> pageableCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        verify(houseService)
                .getMyHouses(eq(username), pageableCaptor.capture());

        assertEquals(expectedPageable, pageableCaptor.getValue());

        verifyNoMoreInteractions(houseService);
    }
}


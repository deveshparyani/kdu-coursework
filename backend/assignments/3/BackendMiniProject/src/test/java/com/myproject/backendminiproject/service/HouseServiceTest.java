package com.myproject.backendminiproject.service;

import com.myproject.backendminiproject.dto.GetHousesResponseDto;
import com.myproject.backendminiproject.entities.House;
import com.myproject.backendminiproject.entities.User;
import com.myproject.backendminiproject.repositories.HouseRepository;
import com.myproject.backendminiproject.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private HouseService houseService;

    private final Pageable pageable = PageRequest.of(0, 5);

    @Test
    public void getMyHouses_success_returnsPagedResponseDto() {
        // given
        String username = "devesh";

        User admin = User.builder()
                .username("devesh")
                .build();

        House h1 = House.builder()
                .address("Jaipur")
                .description("Home 1")
                .admin(admin)   // ✅ FIX
                .build();

        House h2 = House.builder()
                .address("Delhi")
                .description("Home 2")
                .admin(admin)   // ✅ FIX
                .build();

        Page<House> housePage =
                new PageImpl<>(List.of(h1, h2), pageable, 2);

        when(houseRepository.findMyHouses(username, pageable))
                .thenReturn(housePage);

        // when
        Page<GetHousesResponseDto> result =
                houseService.getMyHouses(username, pageable);

        // then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        assertEquals("Jaipur", result.getContent().get(0).getAddress());
        assertEquals("Delhi", result.getContent().get(1).getAddress());

        assertEquals("devesh", result.getContent().get(0).getAdminUsername());

        verify(houseRepository).findMyHouses(username, pageable);
        verifyNoMoreInteractions(houseRepository);
    }


    @Test
    public void getMyHouses_noHouses_returnsEmptyPage() {
        // given
        String username = "devesh";

        Page<House> emptyPage = Page.empty(pageable);

        when(houseRepository.findMyHouses(username, pageable))
                .thenReturn(emptyPage);

        // when
        Page<GetHousesResponseDto> result =
                houseService.getMyHouses(username, pageable);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(houseRepository).findMyHouses(username, pageable);
    }
}

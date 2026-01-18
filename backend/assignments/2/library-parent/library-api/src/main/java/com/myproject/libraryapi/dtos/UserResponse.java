package com.myproject.libraryapi.dtos;


import com.myproject.libraryapi.dtos.enums.UserRoles;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO representing a user.
 */
@Data
@Value
@Builder
public class UserResponse {

    private UUID id;
    private String username;
    private UserRoles role;
    private Instant createdAt;

    // getters & setters
}
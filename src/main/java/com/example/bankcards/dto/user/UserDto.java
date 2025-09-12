package com.example.bankcards.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
}

package com.example.bankcards.dto.auth;

import lombok.Value;

@Value
public class LoginResponseDto {
    private final String token;
    private final String tokenType;
}

package com.example.bankcards.dto.auth;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class LoginRequestDto {
    @NotBlank
    @Size(min = 2, max = 254)
    private final String login;

    @NotBlank
    @Size(min = 2, max = 128)
    private final String password;
}

package com.example.bankcards.dto.auth;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class RegisterDto {
    @NotBlank
    @Size(min = 2, max = 128)
    private final String username;

    @NotBlank
    @Size(min = 2, max = 128)
    private final String password;

    @NotNull
    @Size(min = 6, max = 254)
    @Email
    private final String email;
}

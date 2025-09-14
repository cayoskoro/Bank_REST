package com.example.bankcards.controller;

import com.example.bankcards.dto.auth.LoginRequestDto;
import com.example.bankcards.dto.auth.LoginResponseDto;
import com.example.bankcards.dto.auth.RegisterDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(name = "/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Public: Аутентификация", description = "Public API для аутентификации")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}

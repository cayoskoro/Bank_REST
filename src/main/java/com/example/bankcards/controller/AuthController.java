package com.example.bankcards.controller;

import com.example.bankcards.dto.auth.LoginRequestDto;
import com.example.bankcards.dto.auth.LoginResponseDto;
import com.example.bankcards.dto.auth.RegisterDto;
import com.example.bankcards.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Public: Аутентификация", description = "Public API для аутентификации")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "409", description = "Нарушение целостности данных")
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterDto registerDto) {
        authService.register(registerDto);
    }

    @Operation(summary = "Аутентификация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован"),
            @ApiResponse(responseCode = "409", description = "Нарушение целостности данных")
    })
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}

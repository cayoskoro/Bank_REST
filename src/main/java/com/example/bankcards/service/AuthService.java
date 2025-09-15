package com.example.bankcards.service;

import com.example.bankcards.dto.auth.LoginRequestDto;
import com.example.bankcards.dto.auth.LoginResponseDto;
import com.example.bankcards.dto.auth.RegisterDto;

public interface AuthService {
    public void register(RegisterDto registerDto);

    public LoginResponseDto login(LoginRequestDto loginRequestDto);
}

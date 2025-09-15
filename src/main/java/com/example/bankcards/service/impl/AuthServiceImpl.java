package com.example.bankcards.service.impl;

import com.example.bankcards.dto.auth.LoginRequestDto;
import com.example.bankcards.dto.auth.LoginResponseDto;
import com.example.bankcards.dto.auth.RegisterDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.TokenService;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {
        log.info("Регистрация пользователя");
        String defaultRole = "ROLE_USER";
        Role role = roleRepository.findByName(defaultRole).orElseThrow(() -> {
            log.info("Ошибка при регистрации пользователя. Роль = {} не существует среди возможных.", defaultRole);
            return new NotFoundException("Ошибка при регистрации пользователя. Роль = " + defaultRole
                    + " не существует среди возможных.");
        });
        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getLogin(), loginRequestDto.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generateToken(userDetails);
        return new LoginResponseDto(token, "Bearer");
    }
}

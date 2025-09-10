package com.example.bankcards.service;

import com.example.bankcards.dto.user.UserRequestDto;
import com.example.bankcards.dto.user.UserResponseDto;

import java.util.Collection;

public interface UserService {
    public Collection<UserResponseDto> getAllUsers(Collection<Long> ids, int from, int size);

    public UserResponseDto addNewUser(UserRequestDto userRequestDto);

    public void deleteUser(long userId);
}

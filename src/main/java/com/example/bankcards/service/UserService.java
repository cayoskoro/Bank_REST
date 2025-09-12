package com.example.bankcards.service;

import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;

import java.util.Collection;

public interface UserService {
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size);

    public UserDto addNewUser(NewUserDto newUserDto);

    public void deleteUser(long userId);
}

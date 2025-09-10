package com.example.bankcards.mapper;

import com.example.bankcards.dto.user.UserRequestDto;
import com.example.bankcards.dto.user.UserResponseDto;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface UserMapper {
    public User convertToEntity(UserRequestDto dto);

    public UserResponseDto convertToDto(User entity);

    public Collection<UserResponseDto> convertToDtoCollection(Collection<User> entities);

}

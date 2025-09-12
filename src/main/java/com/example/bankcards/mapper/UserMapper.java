package com.example.bankcards.mapper;

import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface UserMapper {
    public User convertToEntity(NewUserDto dto);

    public UserDto convertToDto(User entity);

    public Collection<UserDto> convertToDtoCollection(Collection<User> entities);

}

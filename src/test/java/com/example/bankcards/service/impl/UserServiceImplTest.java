package com.example.bankcards.service.impl;

import com.example.bankcards.UserBaseTest;
import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {"db.name=test"})
class UserServiceImplTest extends UserBaseTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();

        Mockito.when(userMapper.convertToDto(user1)).thenReturn(userDto1);
        Mockito.when(userMapper.convertToDto(user2)).thenReturn(userDto2);
        Mockito.when(userMapper.convertToEntity(Mockito.any(NewUserDto.class))).thenReturn(user2);

        Mockito.when(userMapper.convertToDtoCollection(List.of(user1, user2)))
                .thenReturn(List.of(userDto1, userDto2));
        Mockito.when(userMapper.convertToDtoCollection(Collections.singletonList(user2)))
                .thenReturn(Collections.singletonList(userDto2));
        Mockito.when(userMapper.convertToDtoCollection(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

    }

    @Test
    void shouldGetAllUsersWithDefaultParameters() {
        Mockito.when(userRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(user1, user2)));
        Collection<UserDto> userDtos = userService.getAllUsers(null, 0, 10);
        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals(List.of(userDto1, userDto2), userDtos);
    }

    @Test
    void shouldGetAllUsersWithWithIdsIsNullFrom1AndSize1() {
        Mockito.when(userRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(user2)));
        Collection<UserDto> userDtos = userService.getAllUsers(null, 1, 1);
        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals(List.of(userDto2), userDtos);
    }

    @Test
    void shouldGetAllUsersWithIdsContainsUser2AndDefaultParameters() {
        Collection<Long> ids = Collections.singletonList(user2.getId());
        Mockito.when(userRepository.findByIdIn(Mockito.any(), Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(user2)));
        Collection<UserDto> userDtos = userService.getAllUsers(ids, 0, 10);
        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals(List.of(userDto2), userDtos);
    }

    @Test
    void shouldGetAllUsersWithoutUsers() {
        Mockito.when(userRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        Collection<UserDto> userDtos = userService.getAllUsers(null, 0, 10);
        assertNotNull(userDtos);
        assertEquals(0, userDtos.size());
        assertEquals(Collections.emptyList(), userDtos);
    }


    @Test
    void shouldAddNewUser() {
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user2);
        UserDto userDto = userService.addNewUser(newUserDto2);
        assertNotNull(userDto);
        assertEquals(userDto2, userDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(user2);
    }

    @Test
    void deleteUser() {
        Mockito.when(userRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        userService.deleteUser(1);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}
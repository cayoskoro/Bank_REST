package com.example.bankcards;

import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import org.junit.jupiter.api.BeforeEach;

import java.util.Set;

public class UserBaseTest {
    protected User user1;
    protected User user2;
    protected Role roleAdmin;
    protected Role roleUser;
    protected UserDto userDto1;
    protected UserDto userDto2;
    protected NewUserDto newUserDto2;


    @BeforeEach
    protected void setUp() {
        roleAdmin = Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();
        roleUser = Role.builder()
                .id(2L)
                .name("ROLE_USER")
                .build();

        user1 = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@email.com")
                .password("1234")
                .roles(Set.of(roleAdmin))
                .build();
        user2 = User.builder()
                .id(2L)
                .username("user")
                .email("user@email.com")
                .password("4321")
                .roles(Set.of(roleUser))
                .build();

        userDto1 = UserDto.builder()
                .username(user1.getUsername())
                .email(user1.getEmail())
                .build();
        userDto2 = UserDto.builder()
                .username(user2.getUsername())
                .email(user2.getEmail())
                .build();

        newUserDto2 = NewUserDto.builder()
                .username(user2.getUsername())
                .email(user2.getEmail())
                .build();

    }
}

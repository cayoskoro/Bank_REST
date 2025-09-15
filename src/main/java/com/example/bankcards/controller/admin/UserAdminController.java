package com.example.bankcards.controller.admin;

import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Admin: пользователи", description = "Admin API для работы с пользователями")
public class UserAdminController {
    private final UserService userService;

    @Operation(summary = "Получение информации о пользователях")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выдан список пользователей")
    })
    @GetMapping
    public Collection<UserDto> getAllUsers(@RequestParam(required = false) Collection<Long> ids,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        return userService.getAllUsers(ids, from, size);
    }

    @Operation(summary = "Добавление нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь добавлен"),
            @ApiResponse(responseCode = "409", description = "Нарушение целостности данных")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody @Valid NewUserDto newUserDto) {
        return userService.addNewUser(newUserDto);
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден или недоступен")
    })
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

}

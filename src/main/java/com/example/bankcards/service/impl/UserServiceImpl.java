package com.example.bankcards.service.impl;

import com.example.bankcards.dto.user.NewUserDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getAllUsers(Collection<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        Page<User> users = ids == null || ids.isEmpty()
                ? userRepository.findAll(pageRequest)
                : userRepository.findByIdIn(ids, pageRequest);
        Collection<UserDto> userDtos = userMapper.convertToDtoCollection(users.getContent());
        log.info("Запрос пользователей по списку ids = {} - {}", ids, userDtos);
        return userDtos;
    }

    @Override
    @Transactional
    public UserDto addNewUser(NewUserDto newUserDto) {
        User user = userMapper.convertToEntity(newUserDto);
        UserDto userDto = userMapper.convertToDto(userRepository.save(user));
        log.info("Добавлен новый пользователь - {}", userDto);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        checkUserExists(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь id = {} удален", userId);
    }

    private void checkUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователя id = {} не существует", userId);
            throw new NotFoundException("Пользователя id = " + userId + " не существует");
        }
    }
}

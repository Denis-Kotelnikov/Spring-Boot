package ru.kotelnikov.MusicAppApplication.service;

import ru.kotelnikov.MusicAppApplication.dto.UserDto;
import ru.kotelnikov.MusicAppApplication.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByUsername(String username);
    List<User> getAllUsers();
    void assignRole(Long userId, String roleName);
}
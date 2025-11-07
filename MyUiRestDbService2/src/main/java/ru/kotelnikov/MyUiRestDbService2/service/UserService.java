package ru.kotelnikov.MyUiRestDbService2.service;

import ru.kotelnikov.MyUiRestDbService2.dto.UserDto;
import ru.kotelnikov.MyUiRestDbService2.entity.User;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByEmail (String email);
    List<UserDto> findAllUsers();
}

package ru.kotelnikov.MusicAppApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MusicAppApplication.entity.Role;
import ru.kotelnikov.MusicAppApplication.entity.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
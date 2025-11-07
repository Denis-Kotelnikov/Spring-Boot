package ru.kotelnikov.MusicAppApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MusicAppApplication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
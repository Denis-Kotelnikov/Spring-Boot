package ru.kotelnikov.MyUiRestDbService2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MyUiRestDbService2.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}

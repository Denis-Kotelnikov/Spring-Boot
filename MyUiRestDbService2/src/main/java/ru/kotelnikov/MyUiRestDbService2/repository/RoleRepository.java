package ru.kotelnikov.MyUiRestDbService2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotelnikov.MyUiRestDbService2.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}

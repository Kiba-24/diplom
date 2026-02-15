package ru.diplom.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.support.model.AppUser;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}

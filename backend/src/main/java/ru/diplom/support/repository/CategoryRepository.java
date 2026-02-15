package ru.diplom.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.support.model.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}

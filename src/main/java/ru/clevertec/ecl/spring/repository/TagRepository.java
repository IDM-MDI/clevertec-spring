package ru.clevertec.ecl.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.spring.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameLikeIgnoreCase(String name);
}

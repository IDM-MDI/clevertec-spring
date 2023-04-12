package ru.clevertec.ecl.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.spring.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

package ru.clevertec.ecl.spring.repository;

import jakarta.validation.constraints.NotNull;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.PageFilter;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> findTags(@NotNull PageFilter page);
    List<Tag> findTags(Tag tag);
    Optional<Tag> findTag(long id);
    Tag save(Tag tag);
    Tag update(Tag tag, long id);
    void delete(long id);
}

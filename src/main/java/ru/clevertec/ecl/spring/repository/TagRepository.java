package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> findTags(int page, int size, String filter, String direction);
    List<Tag> findTags(Tag tag);
    Optional<Tag> findTag(long id);
    Tag save(Tag tag);
    Tag update(Tag tag, long id);
    void delete(long id);
}

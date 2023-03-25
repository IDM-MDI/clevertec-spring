package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.Tag;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> findTags(int page, int size, String filter, String direction) throws SQLException;
    List<Tag> findTags(Tag tag) throws SQLException;
    Optional<Tag> findTag(long id) throws SQLException;
    Tag save(Tag tag) throws SQLException;
    Tag update(Tag tag, long id) throws SQLException;
    void delete(long id) throws SQLException;
}

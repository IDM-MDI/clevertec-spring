package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Tag;

import java.sql.SQLException;
import java.util.List;

public interface TagRepository {
    List<Tag> findTags(int page, int size, String filter, String direction) throws SQLException;
    List<Tag> findTags(Tag tag);
    Tag findTag(long id);
    Tag save(Tag tag);
    Tag update(Tag tag);
    boolean delete(long id);
}

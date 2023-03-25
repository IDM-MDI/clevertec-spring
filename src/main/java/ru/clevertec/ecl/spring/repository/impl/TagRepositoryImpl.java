package ru.clevertec.ecl.spring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.TagRowMapper;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate template;
    private final TagRowMapper rowMapper;
    @Override
    public List<Tag> findTags(int page, int size, String filter, String direction) throws SQLException {
        return template.query("", rowMapper);
    }

    @Override
    public List<Tag> findTags(Tag tag) {
        return null;
    }

    @Override
    public Tag findTag(long id) {
        return null;
    }

    @Override
    public Tag save(Tag tag) {
        return null;
    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}

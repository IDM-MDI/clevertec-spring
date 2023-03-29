package ru.clevertec.ecl.spring.repository.impl;

import jakarta.validation.constraints.NotNull;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.RepositoryExceptionMethods;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.TagRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.NAME;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.TAG;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static ru.clevertec.ecl.spring.repository.RepositoryExceptionMethods.findByID;
import static ru.clevertec.ecl.spring.repository.RepositoryExceptionMethods.findEntities;
import static ru.clevertec.ecl.spring.repository.RepositoryExceptionMethods.findEntitiesByPage;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createInsertMap;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByColumn;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.updateByOneColumn;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final TagRowMapper rowMapper;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate template, TagRowMapper rowMapper, DataSource dataSource) {
        this.template = template;
        this.rowMapper = rowMapper;
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TAG)
                .usingGeneratedKeyColumns(ID);
    }

    @Override
    public List<Tag> findTags(@NotNull PageFilter page) {
        return findEntitiesByPage(template, rowMapper, findAllByPage(TAG, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }

    @Override
    public List<Tag> findTags(Tag tag) {
        return findEntities(template, createSearchQuery(tag), rowMapper);
    }

    @Override
    public Optional<Tag> findTag(long id) {
        return findByID(template, findAllByColumn(TAG, ID), rowMapper, String.valueOf(id));
    }

    @Override
    public Tag save(Tag tag) {
        return RepositoryExceptionMethods.save(
                jdbcInsert,
                createInsertMap(tag),
                number -> findTag(number.longValue())
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public Tag update(Tag tag, long id) {
        return RepositoryExceptionMethods.update(
                template,
                updateByOneColumn(TAG, NAME, ID),
                () -> findTag(id).orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString())),
                tag.getName(), String.valueOf(id));
    }
    @Override
    public void delete(long id) {
        RepositoryExceptionMethods.update(
                template,
                updateByOneColumn(TAG, STATUS, ID),
                () -> null,
                DELETED, String.valueOf(id));
    }
}

package ru.clevertec.ecl.spring.repository.impl;

import jakarta.validation.constraints.NotNull;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
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
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createInsertMap;
import static ru.clevertec.ecl.spring.repository.handler.TagHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByColumn;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.updateByOneColumn;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    @Autowired
    public TagRepositoryImpl(JdbcTemplate template, TagRowMapper rowMapper, DataSource dataSource) {
        super(
                template,
                new SimpleJdbcInsert(dataSource)
                        .withTableName(TAG)
                        .usingGeneratedKeyColumns(ID),
                rowMapper
        );
    }

    @Override
    public List<Tag> findTags(@NotNull PageFilter page) {
        return findEntitiesByPage(findAllByPage(TAG, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }

    @Override
    public List<Tag> findTags(Tag tag) {
        return findEntities(createSearchQuery(tag));
    }

    @Override
    public Optional<Tag> findTag(long id) {
        return findOneByColumn(findAllByColumn(TAG, ID), String.valueOf(id));
    }

    @Override
    public Tag save(Tag tag) {
        return super.save(
                createInsertMap(tag),
                number -> findTag(number.longValue())
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public Tag update(Tag tag, long id) {
        return super.update(
                updateByOneColumn(TAG, NAME, ID),
                () -> findTag(id).orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString())),
                tag.getName(), String.valueOf(id));
    }
    @Override
    public void delete(long id) {
        super.update(
                updateByOneColumn(TAG, STATUS, ID),
                () -> null,
                DELETED, String.valueOf(id));
    }
}

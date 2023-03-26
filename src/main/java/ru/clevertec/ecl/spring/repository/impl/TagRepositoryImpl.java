package ru.clevertec.ecl.spring.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.TagRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.NAME;
import static ru.clevertec.ecl.spring.repository.ColumnName.STATUS;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String FIND_BY_PAGE =
            "SELECT * FROM tag " +
            "ORDER BY %s %s " +
            "LIMIT ? OFFSET ?";
    private static final String FIND_BY_COLUMN =
            "SELECT * FROM tag " +
                    "WHERE %s = ?";
    private static final String UPDATE =
            "UPDATE tag " +
            "SET name = ? " +
            "WHERE id = ?";
    private static final String SET_STATUS =
            "UPDATE tag " +
            "SET status = '%s' " +
            "WHERE %s = ?";
    private static final String SEARCH_START = "SELECT * FROM tag WHERE 1=1";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final TagRowMapper rowMapper;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate template, TagRowMapper rowMapper, DataSource dataSource) {
        this.template = template;
        this.rowMapper = rowMapper;
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("tag")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Tag> findTags(int page, int size, String filter, String direction) throws SQLException {
        return template.query(String.format(FIND_BY_PAGE, filter, direction), rowMapper, size, page);
    }

    @Override
    public List<Tag> findTags(Tag tag) throws SQLException {
        return template.query(createSearchQuery(tag),rowMapper);
    }

    @Override
    public Optional<Tag> findTag(long id) throws SQLException {
        return template.query(String.format(FIND_BY_COLUMN, ID), rowMapper, String.valueOf(id))
                .stream()
                .findFirst();
    }

    @Override
    public Tag save(Tag tag) throws SQLException {
        Number number = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(createInsertMap(tag)));
        return findTag(number.longValue())
                .orElseThrow();             //TODO : FINISH EXCEPTION
    }

    @Override
    public Tag update(Tag tag, long id) throws SQLException {
        template.update(UPDATE, tag.getName(), String.valueOf(id));
        return findTag(id)
                .orElseThrow();             //TODO : FINISH EXCEPTION
    }

    @Override
    public void delete(long id) throws SQLException {
        template.execute(String.format(SET_STATUS, DELETED, ID), String.valueOf(id));
    }

    private Map<String, Object> createInsertMap(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME, tag.getName());
        parameters.put(STATUS, ACTIVE);
        return parameters;
    }

    private String createSearchQuery(Tag tag) {
        StringBuilder builder = new StringBuilder(SEARCH_START);
        if(tag.getId() > 0) {
            builder.append(" AND id = ")
                    .append(tag.getId());
        }
        if(StringUtils.isNotBlank(tag.getName())) {
            builder.append(" AND name = ")
                    .append(String.format("'%s'", tag.getName()));
        }
        if(StringUtils.isNotBlank(tag.getStatus())) {
            builder.append(" AND status = ")
                    .append(String.format("'%s'", tag.getStatus()));
        }
        return builder.toString();
    }
}

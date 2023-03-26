package ru.clevertec.ecl.spring.repository.impl;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftTagRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.clevertec.ecl.spring.repository.ColumnName.GIFT_ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.TAG_ID;

@Repository
public class GiftTagRepositoryImpl implements GiftTagRepository {
    private static final String FIND_BY_COLUMN =
            "SELECT * FROM gift_tag " +
                    "WHERE %s = ?";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final GiftTagRowMapper rowMapper;

    @Autowired
    public GiftTagRepositoryImpl(JdbcTemplate template, GiftTagRowMapper rowMapper, DataSource source) {
        this.template = template;
        this.rowMapper = rowMapper;
        jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("gift_tag")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void saveAll(List<GiftTag> relations) {
        relations.forEach(this::save);
    }

    @Override
    public void save(GiftTag relation) {
        jdbcInsert.execute(new MapSqlParameterSource(createInsertMap(relation)));
    }

    @Override
    public List<GiftTag> findByTag(long id) throws SQLException {
        return template.query(String.format(FIND_BY_COLUMN, TAG_ID), rowMapper, String.valueOf(id));
    }

    @Override
    public List<GiftTag> findByGift(long id) throws SQLException {
        return template.query(String.format(FIND_BY_COLUMN, GIFT_ID), rowMapper, String.valueOf(id));
    }
    private Map<String, Object> createInsertMap(GiftTag relation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(GIFT_ID, relation.getGiftID());
        parameters.put(TAG_ID, relation.getTagID());
        return parameters;
    }
}

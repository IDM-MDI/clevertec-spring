package ru.clevertec.ecl.spring.repository.impl;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftTagRowMapper;

import javax.sql.DataSource;
import java.util.List;

import static ru.clevertec.ecl.spring.entity.ColumnName.GIFT_ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.TAG_ID;
import static ru.clevertec.ecl.spring.entity.TableName.GIFT_TAG;
import static ru.clevertec.ecl.spring.repository.handler.GiftTagHandler.createInsertMap;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByColumn;

@Repository
public class GiftTagRepositoryImpl extends AbstractRepository<GiftTag> implements GiftTagRepository {
    @Autowired
    public GiftTagRepositoryImpl(JdbcTemplate template, GiftTagRowMapper rowMapper, DataSource source) {
        super(
                template,
                new SimpleJdbcInsert(source)
                        .withTableName(GIFT_TAG)
                        .usingGeneratedKeyColumns(ID),
                rowMapper);
    }

    @Override
    public void saveAll(List<GiftTag> relations) {
        relations.forEach(this::save);
    }

    @Override
    public void save(GiftTag relation) {
        super.save(createInsertMap(relation), number -> null);
    }

    @Override
    public List<GiftTag> findByTag(long id) {
        return findByColumn(id, TAG_ID);
    }

    @Override
    public List<GiftTag> findByGift(long id) {
        return findByColumn(id, GIFT_ID);
    }

    private List<GiftTag> findByColumn(long id, String column) {
        return super
                .findByColumn(findAllByColumn(GIFT_TAG, column), String.valueOf(id));
    }
}

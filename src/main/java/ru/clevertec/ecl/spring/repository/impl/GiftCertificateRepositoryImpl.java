package ru.clevertec.ecl.spring.repository.impl;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.AbstractRepository;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftCertificateRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.ColumnName.ID;
import static ru.clevertec.ecl.spring.entity.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.entity.TableName.GIFT_CERTIFICATE;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static ru.clevertec.ecl.spring.repository.handler.GiftCertificateHandler.createInsertMap;
import static ru.clevertec.ecl.spring.repository.handler.GiftCertificateHandler.createSearchQuery;
import static ru.clevertec.ecl.spring.repository.handler.GiftCertificateHandler.createUpdateQuery;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByColumn;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.findAllByPage;
import static ru.clevertec.ecl.spring.repository.query.SQLQuery.updateByOneColumn;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate> implements GiftCertificateRepository {
    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate template, GiftCertificateRowMapper rowMapper, DataSource source) {
        super(template,
                new SimpleJdbcInsert(source)
                        .withTableName(GIFT_CERTIFICATE)
                        .usingGeneratedKeyColumns(ID),
                rowMapper);
    }

    @Override
    public List<GiftCertificate> findGifts(PageFilter page) {
        return findEntitiesByPage(findAllByPage(GIFT_CERTIFICATE, page.getFilter(), page.getDirection()), page.getSize(), page.getNumber());
    }

    @Override
    public List<GiftCertificate> findGifts(GiftCertificate certificate) {
        return findEntities(createSearchQuery(certificate));
    }

    @Override
    public Optional<GiftCertificate> findGift(long id) {
        return findOneByColumn(findAllByColumn(GIFT_CERTIFICATE, ID), String.valueOf(id));
    }

    @Override
    public GiftCertificate save(GiftCertificate certificate)  {
        return super.save(
                createInsertMap(certificate),
                number -> findGift(number.longValue())
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate, long id) {
        return super.update(
                createUpdateQuery(certificate),
                () -> findGift(id)
                        .orElseThrow(() -> new RepositoryException(ENTITY_NOT_FOUND.toString())),
                String.valueOf(id)
        );
    }

    @Override
    public void delete(long id) {
        super.update(
                updateByOneColumn(GIFT_CERTIFICATE, STATUS, ID),
                () -> null,
                DELETED, String.valueOf(id));
    }
}

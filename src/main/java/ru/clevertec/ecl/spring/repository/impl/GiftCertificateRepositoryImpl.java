package ru.clevertec.ecl.spring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftCertificateRowMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.repository.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.repository.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.repository.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.NAME;
import static ru.clevertec.ecl.spring.repository.ColumnName.PRICE;
import static ru.clevertec.ecl.spring.repository.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.repository.ColumnName.UPDATE_DATE;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String FIND_BY_PAGE =
            "SELECT * FROM gift_certificate " +
                    "ORDER BY %s %s " +
                    "OFFSET ? LIMIT ?";
    private static final String FIND_BY_COLUMN =
            "SELECT * FROM gift_certificate " +
                    "WHERE %s = ?";
    private static final String UPDATE =
            "UPDATE gift_certificate " +
                    "SET " +
                    "WHERE id = ? ";
    private static final String SET_STATUS =
            "UPDATE gift_certificate " +
                    "SET status = %s " +
                    "WHERE %s = ?";
    private static final String SEARCH_START = "SELECT * FROM gift_certificate WHERE 1=1";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final GiftCertificateRowMapper rowMapper;
    @Override
    public List<GiftCertificate> findGifts(int page, int size, String filter, String direction) throws SQLException {
        return template.query(String.format(FIND_BY_PAGE, filter, direction), rowMapper, page, size);
    }

    @Override
    public List<GiftCertificate> findGifts(GiftCertificate certificate) throws SQLException {
        return template.query(createSearchQuery(certificate),rowMapper);
    }

    @Override
    public Optional<GiftCertificate> findGift(long id) throws SQLException {
        return template.query(String.format(FIND_BY_COLUMN, ID), rowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public GiftCertificate save(GiftCertificate certificate) throws SQLException {
        Number number = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(createInsertMap(certificate)));
        return findGift(number.longValue())
                .orElseThrow();
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate, long id) throws SQLException {
        template.update(createUpdateQuery(certificate),id);
        return findGift(id)
                .orElseThrow();
    }

    @Override
    public void delete(long id) throws SQLException {
        template.execute(String.format(SET_STATUS, ACTIVE, ID), id);
    }

    private Map<String, Object> createInsertMap(GiftCertificate certificate) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(NAME, certificate.getName());
        parameters.put(DESCRIPTION, certificate.getDescription());
        parameters.put(PRICE,certificate.getPrice());
        parameters.put(DURATION, certificate.getDuration());
        parameters.put(CREATE_DATE, LocalDateTime.now());
        parameters.put(UPDATE_DATE, LocalDateTime.now());
        parameters.put(STATUS, ACTIVE);
        return parameters;
    }
    private String createSearchQuery(GiftCertificate certificate) {
        StringBuilder builder = new StringBuilder(SEARCH_START);
        if(certificate.getId() > 0) {
            builder.append(" AND id = ")
                    .append(certificate.getId());
        }
        if(StringUtils.isNotBlank(certificate.getName())) {
            builder.append(" AND name = ")
                    .append(certificate.getName());
        }
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.append(" AND description = ")
                    .append(certificate.getDescription());
        }
        if(Objects.nonNull(certificate.getPrice())) {
            builder.append(" AND price = ")
                    .append(certificate.getPrice());
        }
        if(certificate.getDuration() > 0) {
            builder.append(" AND duration = ")
                    .append(certificate.getDuration());
        }
        if(Objects.nonNull(certificate.getCreateDate())) {
            builder.append(" AND create_date = ")
                    .append(certificate.getCreateDate().toString());
        }
        if(Objects.nonNull(certificate.getUpdateDate())) {
            builder.append(" AND update_date = ")
                    .append(certificate.getUpdateDate().toString());
        }
        if(StringUtils.isNotBlank(certificate.getName())) {
            builder.append(" AND status = ")
                    .append(certificate.getStatus());
        }
        return builder.toString();
    }
    public static String createUpdateQuery(GiftCertificate certificate) {
        StringBuilder builder = new StringBuilder(UPDATE);
        if(StringUtils.isNotBlank(certificate.getName())) {
            builder.append(NAME)
                    .append(" = ")
                    .append(certificate.getName())
                    .append(",");
        }
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.append(DESCRIPTION)
                    .append(" = ")
                    .append(certificate.getDescription())
                    .append(",");
        }
        if(certificate.getDuration() > 0) {
            builder.append(DURATION)
                    .append(" = ")
                    .append(certificate.getDuration())
                    .append(",");
        }
        if(Objects.nonNull(certificate.getPrice())) {
            builder.append(PRICE)
                    .append(" = ")
                    .append(certificate.getPrice());
        }
        if(Objects.nonNull(certificate.getUpdateDate())) {
            builder.append(UPDATE_DATE)
                    .append(" = ")
                    .append(certificate.getUpdateDate());
        }
        return builder.toString();
    }
}

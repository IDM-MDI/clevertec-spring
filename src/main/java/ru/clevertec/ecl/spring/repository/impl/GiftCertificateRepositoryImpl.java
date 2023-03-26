package ru.clevertec.ecl.spring.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftCertificateRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_FIELDS_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_SQL_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.OTHER_REPOSITORY_EXCEPTION;
import static ru.clevertec.ecl.spring.repository.ColumnName.CREATE_DATE;
import static ru.clevertec.ecl.spring.repository.ColumnName.DESCRIPTION;
import static ru.clevertec.ecl.spring.repository.ColumnName.DURATION;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;
import static ru.clevertec.ecl.spring.repository.ColumnName.NAME;
import static ru.clevertec.ecl.spring.repository.ColumnName.PRICE;
import static ru.clevertec.ecl.spring.repository.ColumnName.STATUS;
import static ru.clevertec.ecl.spring.repository.ColumnName.UPDATE_DATE;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String FIND_BY_PAGE =
            "SELECT * FROM gift_certificate " +
                    "ORDER BY %s %s " +
                    "LIMIT ? OFFSET ?";
    private static final String FIND_BY_COLUMN =
            "SELECT * FROM gift_certificate " +
                    "WHERE %s = ?";
    private static final String UPDATE =
            "UPDATE gift_certificate " +
                    "SET ";
    private static final String WHERE_ID = " WHERE id = ?";
    private static final String SET_STATUS =
            "UPDATE gift_certificate " +
                    "SET status = '%s' " +
                    "WHERE %s = ?";
    private static final String SEARCH_START = "SELECT * FROM gift_certificate WHERE 1=1";
    private static final String LIKE_STRING = " LIKE '%s'";
    private static final String IS_STRING = "= '%s'";
    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final GiftCertificateRowMapper rowMapper;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate template, GiftCertificateRowMapper rowMapper, DataSource source) {
        this.template = template;
        this.rowMapper = rowMapper;
        jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("gift_certificate")
                .usingGeneratedKeyColumns(ID);
    }

    @Override
    public List<GiftCertificate> findGifts(int page, int size, String filter, String direction) {
        try {
            return template.query(String.format(FIND_BY_PAGE, filter, direction), rowMapper, size, page);
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public List<GiftCertificate> findGifts(GiftCertificate certificate) {
        try {
            return template.query(createSearchQuery(certificate), rowMapper);
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public Optional<GiftCertificate> findGift(long id) {
        try {
            return template.query(String.format(FIND_BY_COLUMN, ID), rowMapper, String.valueOf(id))
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public GiftCertificate save(GiftCertificate certificate)  {
        try {
            Number number = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(createInsertMap(certificate)));
            return findGift(number.longValue())
                    .orElseThrow();

        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate, long id) {
        try {
            template.update(createUpdateQuery(certificate),String.valueOf(id));
            return findGift(id)
                    .orElseThrow();

        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION + e.getMessage());
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            template.execute(String.format(SET_STATUS, DELETED, ID), String.valueOf(id));
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION + e.getMessage());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION + e.getMessage());
        }
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
            builder.append(String.format(" AND LOWER(name)" + LIKE_STRING, "%" + certificate.getName().toLowerCase() + "%"));
        }
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.append(String.format(" AND LOWER(description)" + LIKE_STRING, "%"+ certificate.getDescription().toLowerCase() + "%"));
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
            builder.append(String.format(" AND LOWER(create_date)" + LIKE_STRING, "%" + certificate.getCreateDate().toString().toLowerCase() + "%"));
        }
        if(Objects.nonNull(certificate.getUpdateDate())) {
            builder.append(String.format(" AND LOWER(update_date)" + LIKE_STRING, "%"+ certificate.getUpdateDate().toString().toLowerCase() + "%"));
        }
        if(StringUtils.isNotBlank(certificate.getStatus())) {
            builder.append(String.format(" AND LOWER(status)" + LIKE_STRING, "%"+ certificate.getStatus().toLowerCase() + "%"));
        }
        return builder.toString();
    }
    private static String createUpdateQuery(GiftCertificate certificate) {
        StringBuilder builder = new StringBuilder(UPDATE);
        if(StringUtils.isNotBlank(certificate.getName())) {
            builder.append(NAME)
                    .append(String.format(IS_STRING, certificate.getName()))
                    .append(",");
        }
        if(StringUtils.isNotBlank(certificate.getDescription())) {
            builder.append(DESCRIPTION)
                    .append(String.format(IS_STRING, certificate.getDescription()))
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
                    .append(certificate.getPrice())
                    .append(",");
        }
        return builder.append(UPDATE_DATE)
                .append(String.format(IS_STRING, LocalDateTime.now()))
                .append(WHERE_ID)
                .toString();
    }
}

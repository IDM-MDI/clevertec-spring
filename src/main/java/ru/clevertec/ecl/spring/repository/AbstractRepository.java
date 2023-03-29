package ru.clevertec.ecl.spring.repository;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.jdbc.RowMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.clevertec.ecl.spring.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_FIELDS_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_SQL_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.OTHER_REPOSITORY_EXCEPTION;

public interface AbstractRepository {
    static <T> T update(JdbcTemplate template, String query, Supplier<T> returnValue, Object... args) {
        try {
            template.execute(query, args);
            return returnValue.get();
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION.toString());
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    static <T> Optional<T> findByID(JdbcTemplate template, String query, RowMapper<T> rowMapper, String value) {
        return findByColumn(template, query, rowMapper, value)
                .stream()
                .findFirst();
    }
    static <T> List<T> findByColumn(JdbcTemplate template, String query, RowMapper<T> rowMapper, String value) {
        try {
            return template.query(query, rowMapper, value);
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    static  <T> List<T> findEntities(JdbcTemplate template, String query, RowMapper<T> rowMapper) {
        try {
            return template.query(query, rowMapper);
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    static <T> List<T> findEntitiesByPage(JdbcTemplate template, RowMapper<T> rowMapper, String query, int size, int page) {
        try {
            return template.query(query, rowMapper, size, page);
        } catch (SQLException e) {
            throw new RepositoryException(ENTITY_SQL_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
    static <T> T save(SimpleJdbcInsert jdbcInsert, Map<String, Object> insertMap, Function<Number,T> returnValue) {
        try {
            return returnValue.apply(jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(insertMap)));
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(ENTITY_FIELDS_EXCEPTION.toString());
        } catch (Exception e) {
            throw new RepositoryException(OTHER_REPOSITORY_EXCEPTION.toString());
        }
    }
}

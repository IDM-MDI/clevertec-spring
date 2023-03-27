package ru.clevertec.ecl.spring.repository;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.jdbc.RowMapper;
import ru.clevertec.ecl.spring.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_SQL_EXCEPTION;
import static ru.clevertec.ecl.spring.exception.ExceptionStatus.OTHER_REPOSITORY_EXCEPTION;
import static ru.clevertec.ecl.spring.repository.ColumnName.ID;

public interface RepositoryExceptionMethods {
    static void deleteExceptionMethod(long id, JdbcTemplate template, String setStatus) {
        try {
            template.execute(String.format(setStatus, DELETED, ID), String.valueOf(id));
        } catch (SQLException e) {
            throw new RepositoryException(String.format(ENTITY_SQL_EXCEPTION.toString(), e.getMessage()));
        } catch (Exception e) {
            throw new RepositoryException(String.format(OTHER_REPOSITORY_EXCEPTION.toString(), e.getMessage()));
        }
    }
    static <T> Optional<T> findByID(JdbcTemplate template , long id, String query, RowMapper<T> rowMapper) {
        try {
            return template.query(query, rowMapper, String.valueOf(id))
                    .stream()
                    .findFirst();
        } catch (SQLException e) {
            throw new RepositoryException(String.format(ENTITY_SQL_EXCEPTION.toString(), e.getMessage()));
        } catch (Exception e) {
            throw new RepositoryException(String.format(OTHER_REPOSITORY_EXCEPTION.toString(), e.getMessage()));
        }
    }
    static  <T> List<T> findEntities(JdbcTemplate template, String query, RowMapper<T> rowMapper) {
        try {
            return template.query(query, rowMapper);
        } catch (SQLException e) {
            throw new RepositoryException(String.format(ENTITY_SQL_EXCEPTION.toString(), e.getMessage()));
        } catch (Exception e) {
            throw new RepositoryException(String.format(OTHER_REPOSITORY_EXCEPTION.toString(), e.getMessage()));
        }
    }
    static <T> List<T> findEntitiesByPage(JdbcTemplate template, RowMapper<T> rowMapper, String query, int page, int size) {
        try {
            return template.query(query, rowMapper, size, page);
        } catch (SQLException e) {
            throw new RepositoryException(String.format(ENTITY_SQL_EXCEPTION.toString(), e.getMessage()));
        } catch (Exception e) {
            throw new RepositoryException(String.format(OTHER_REPOSITORY_EXCEPTION.toString(), e.getMessage()));
        }
    }
}

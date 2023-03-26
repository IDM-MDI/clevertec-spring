package ru.clevertec.ecl.spring.repository.impl;


import org.assertj.core.api.Assertions;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.repository.rowmapper.TagRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

class TagRepositoryImplTest {
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    private TagRepositoryImpl repository;
    private EmbeddedDatabase db;

    @BeforeEach
    void setup() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/migration/dev/V1.0.0__InitDB.sql")
                .addScript("classpath:db/migration/dev/V1.0.2__InitTags.sql")
                .build();
        repository = new TagRepositoryImpl(new JdbcTemplate(db.getConnection()), new TagRowMapper(), db);
    }

    @AfterEach
    void teardown() {
        db.shutdown();
    }
    @Test
    void findTagsByPageShouldReturnCorrectList() throws SQLException {
        List<Tag> result = repository.findTags(PAGE, SIZE, FILTER, DIRECTION);
        Assertions.assertThat(result)
                .isNotEmpty();
    }
    @Test
    void findTagsByPageShouldReturnEmptyList() throws SQLException {
        List<Tag> result = repository.findTags(PAGE + 100, SIZE, FILTER, DIRECTION);
        Assertions.assertThat(result)
                .isEmpty();
    }
    @Test
    void findTagsByTagShouldReturnCorrectList() throws SQLException {
        Tag tag = Tag.builder()
                .name("Entertainment")
                .build();
        List<Tag> result = repository.findTags(tag);
        Assertions.assertThat(result)
                .isNotEmpty();
    }
    @Test
    void findTagByIDShouldReturnPresentValue() throws SQLException {
        Optional<Tag> result = repository.findTag(1L);
        Assertions.assertThat(result)
                .isPresent();
    }
    @Test
    void findTagByIDShouldReturnEmptyValue() throws SQLException {
        Optional<Tag> result = repository.findTag(0L);
        Assertions.assertThat(result)
                .isNotPresent();
    }

    @Test
    void saveShouldReturnSavedTag() throws SQLException {
        String exceptionName = "Test tag";
        Tag result = repository.save(
                Tag.builder()
                        .name(exceptionName)
                        .build()
        );
        Assertions.assertThat(result.getName())
                .isEqualTo(exceptionName);
    }

    @Test
    void updateShouldReturnUpdatedTag() throws SQLException {
        Tag expected = Tag.builder()
                .id(1L)
                .name("test")
                .status(ACTIVE)
                .build();
        Tag result = repository.update(expected,expected.getId());
        Assertions.assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    void deleteShouldChangeStatusToDelete() throws SQLException {
        repository.delete(1L);
        Tag result = repository.findTag(1L)
                .get();
        Assertions.assertThat(result.getStatus())
                .isEqualTo(DELETED);
    }
}
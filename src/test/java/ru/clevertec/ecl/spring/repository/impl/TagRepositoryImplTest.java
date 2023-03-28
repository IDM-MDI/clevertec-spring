package ru.clevertec.ecl.spring.repository.impl;


import org.assertj.core.api.Assertions;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.rowmapper.TagRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

class TagRepositoryImplTest {
    private TagRepositoryImpl repository;
    private EmbeddedDatabase db;
    private PageFilter page;

    @BeforeEach
    void setup() throws SQLException {
        page = new PageFilter();
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
    @Nested
    class FindByPage {
        @Test
        void findTagsByPageShouldReturnCorrectList() {
            List<Tag> result = repository.findTags(page);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByPageShouldReturnEmptyList() {
            page.setNumber(100);
            List<Tag> result = repository.findTags(page);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByTag {
        @Test
        void findTagsByTagShouldReturnCorrectList() {
            Tag tag = Tag.builder()
                    .name("Entertainment")
                    .build();
            List<Tag> result = repository.findTags(tag);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByTagShouldReturnEmptyList() {
            Tag tag = Tag.builder()
                    .id(100L)
                    .build();
            List<Tag> result = repository.findTags(tag);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByID {
        @Test
        void findTagByIDShouldReturnPresentValue() {
            Optional<Tag> result = repository.findTag(1L);
            Assertions.assertThat(result)
                    .isPresent();
        }
        @Test
        void findTagByIDShouldReturnEmptyValue() {
            Optional<Tag> result = repository.findTag(0L);
            Assertions.assertThat(result)
                    .isNotPresent();
        }

    }

    @Nested
    class Save {
        @Test
        void saveShouldReturnSavedTag() {
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
        void saveShouldThrowSQLException() {
            Assertions.assertThatThrownBy(() -> repository.save(Tag.builder().build()))
                    .isInstanceOf(RepositoryException.class);
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnUpdatedTag() {
            Tag expected = Tag.builder()
                    .id(1L)
                    .name("test")
                    .status(ACTIVE)
                    .build();
            Tag result = repository.update(expected,expected.getId());
            Assertions.assertThat(result)
                    .isEqualTo(expected);
        }
    }
    @Nested
    class Delete {
        @Test
        void deleteShouldChangeStatusToDelete() {
            repository.delete(1L);
            Tag result = repository.findTag(1L)
                    .get();
            Assertions.assertThat(result.getStatus())
                    .isEqualTo(DELETED);
        }
    }
}
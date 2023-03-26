package ru.clevertec.ecl.spring.repository.impl;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftCertificateRowMapper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

class GiftCertificateRepositoryImplTest {
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    private GiftCertificateRepositoryImpl repository;
    private EmbeddedDatabase db;

    @BeforeEach
    void setup() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/migration/dev/V1.0.0__InitDB.sql")
                .addScript("classpath:db/migration/dev/V1.0.1__InitGifts.sql")
                .build();
        repository = new GiftCertificateRepositoryImpl(new JdbcTemplate(db.getConnection()), new GiftCertificateRowMapper(), db);
    }

    @AfterEach
    void teardown() {
        db.shutdown();
    }
    @Nested
    class FindByPage {
        @Test
        void findTagsByPageShouldReturnCorrectList() throws SQLException {
            List<GiftCertificate> result = repository.findGifts(PAGE, SIZE, FILTER, DIRECTION);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByPageShouldReturnEmptyList() throws SQLException {
            List<GiftCertificate> result = repository.findGifts(PAGE + 100, SIZE, FILTER, DIRECTION);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByTag {
        @Test
        void findTagsByTagShouldReturnCorrectList() throws SQLException {
            GiftCertificate gift = GiftCertificate.builder()
                    .name("Amazon Gift Card")
                    .build();
            List<GiftCertificate> result = repository.findGifts(gift);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByTagShouldReturnEmptyList() throws SQLException {
            GiftCertificate certificate = GiftCertificate.builder()
                    .id(100L)
                    .build();
            List<GiftCertificate> result = repository.findGifts(certificate);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByID {
        @Test
        void findTagByIDShouldReturnPresentValue() throws SQLException {
            Optional<GiftCertificate> result = repository.findGift(1L);
            Assertions.assertThat(result)
                    .isPresent();
        }
        @Test
        void findTagByIDShouldReturnEmptyValue() throws SQLException {
            Optional<GiftCertificate> result = repository.findGift(0L);
            Assertions.assertThat(result)
                    .isNotPresent();
        }

    }

    @Nested
    class Save {
        @Test
        void saveShouldReturnSavedTag() throws SQLException {
            String exceptionName = "Test tag";
            GiftCertificate result = repository.save(
                    GiftCertificate.builder()
                            .name(exceptionName)
                            .description("test description")
                            .price(new BigDecimal(100))
                            .duration(100)
                            .build()
            );
            Assertions.assertThat(result.getName())
                    .isEqualTo(exceptionName);
        }
        @Test
        void saveShouldThrowSQLException() throws SQLException {
            Assertions.assertThatThrownBy(() -> repository.save(GiftCertificate.builder().build()))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnUpdatedTag() throws SQLException {
            GiftCertificate expected = GiftCertificate.builder()
                    .id(1L)
                    .name("test")
                    .build();
            GiftCertificate result = repository.update(expected,expected.getId());
            Assertions.assertThat(result.getName())
                    .isEqualTo(expected.getName());
        }
    }
    @Nested
    class Delete {
        @Test
        void deleteShouldChangeStatusToDelete() throws SQLException {
            repository.delete(1L);
            GiftCertificate result = repository.findGift(1L)
                    .get();
            Assertions.assertThat(result.getStatus())
                    .isEqualTo(DELETED);
        }
    }

}
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
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftCertificateRowMapper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

class GiftCertificateRepositoryImplTest {
    private static final String MORE_THAN_255 = "asodkasdaslkjelksajdklasjeklasjdalskjdaksjdlaksjdasldjkaslkdjaskldjaslkdjaslkdjaslkdjasdlakjewklajsdlkajsdklasjdlkasdjaklsdjkasljdlkasjdlkajwelkajsldkjaslkdjaslkdjaslkdjalskdjaklsdjalksdjakljeklwajklasjdklasjdklasdjklasjdaklwjeklajsdlkasjdklajskldjasdasdasd";
    private GiftCertificateRepositoryImpl repository;
    private EmbeddedDatabase db;
    private PageFilter page;

    @BeforeEach
    void setup() throws SQLException {
        page = new PageFilter();
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
        void findTagsByPageShouldReturnCorrectList() {
            List<GiftCertificate> result = repository.findGifts(page);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByPageShouldReturnEmptyList() {
            page.setNumber(100);
            List<GiftCertificate> result = repository.findGifts(page);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByTag {
        @Test
        void findTagsByTagShouldReturnCorrectList() {
            GiftCertificate gift = GiftCertificate.builder()
                    .name("Amazon")
                    .build();
            List<GiftCertificate> result = repository.findGifts(gift);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByTagShouldReturnEmptyList() {
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
        void findTagByIDShouldReturnPresentValue() {
            Optional<GiftCertificate> result = repository.findGift(1L);
            Assertions.assertThat(result)
                    .isPresent();
        }
        @Test
        void findTagByIDShouldReturnEmptyValue() {
            Optional<GiftCertificate> result = repository.findGift(0L);
            Assertions.assertThat(result)
                    .isNotPresent();
        }

    }

    @Nested
    class Save {
        @Test
        void saveShouldReturnSavedTag() {
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
        void saveShouldThrowDataIntegrityViolationException() {
            Assertions.assertThatThrownBy(() -> repository.save(GiftCertificate.builder().build()))
                    .isInstanceOf(RepositoryException.class);
        }
        @Test
        void saveShouldThrowSQLException() {
            Assertions.assertThatThrownBy(() -> repository.save(
                            GiftCertificate.builder()
                                    .name(MORE_THAN_255)
                                    .description("test description")
                                    .price(new BigDecimal(100))
                                    .duration(100)
                                    .build()
                    ))
                    .isInstanceOf(RepositoryException.class);
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnUpdatedTag() {
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
        void deleteShouldChangeStatusToDelete() {
            repository.delete(1L);
            GiftCertificate result = repository.findGift(1L)
                    .get();
            Assertions.assertThat(result.getStatus())
                    .isEqualTo(DELETED);
        }
    }

}
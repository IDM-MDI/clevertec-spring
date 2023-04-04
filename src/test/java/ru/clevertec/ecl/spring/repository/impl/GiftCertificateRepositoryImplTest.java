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

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder.aGift;
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
        repository = new GiftCertificateRepositoryImpl();
    }

    @AfterEach
    void teardown() {
        db.shutdown();
    }
    @Nested
    class FindByPage {
        @Test
        void findGiftsByPageShouldReturnCorrectList() {
            List<GiftCertificate> result = repository.findGifts(page);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findGiftsByPageShouldReturnEmptyList() {
            page.setNumber(100);
            List<GiftCertificate> result = repository.findGifts(page);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByGift {
        @Test
        void findGiftsByGiftShouldReturnCorrectList() {
            GiftCertificate gift = GiftCertificate.builder()
                    .description("gift card")
                    .build();
            List<GiftCertificate> result = repository.findGifts(gift);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findGiftsByGiftShouldReturnEmptyList() {
            GiftCertificate certificate = aGift().buildToEntity();
            List<GiftCertificate> result = repository.findGifts(certificate);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
    @Nested
    class FindByID {
        @Test
        void findGiftByIDShouldReturnPresentValue() {
            Optional<GiftCertificate> result = repository.findGift(1L);
            Assertions.assertThat(result)
                    .isPresent();
        }
        @Test
        void findGiftByIDShouldReturnEmptyValue() {
            Optional<GiftCertificate> result = repository.findGift(0L);
            Assertions.assertThat(result)
                    .isNotPresent();
        }

    }

    @Nested
    class Save {
        @Test
        void saveShouldReturnSavedGift() {
            String exceptionName = "Test Gift";
            GiftCertificate result = repository.save(aGift().setName(exceptionName).buildToEntity());
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
                            aGift()
                                    .setId(0)
                                    .setName(MORE_THAN_255)
                                    .buildToEntity()
                    ))
                    .isInstanceOf(RepositoryException.class);
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnUpdatedGift() {
            GiftCertificate expected = aGift().setName("test").buildToEntity();
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
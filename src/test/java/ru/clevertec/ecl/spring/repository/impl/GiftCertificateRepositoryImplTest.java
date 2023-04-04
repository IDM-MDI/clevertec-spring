package ru.clevertec.ecl.spring.repository.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.config.TestRepository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder.aGift;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

@Transactional
class GiftCertificateRepositoryImplTest {
    private static final String MORE_THAN_255 = "asodkasdaslkjelksajdklasjeklasjdalskjdaksjdlaksjdasldjkaslkdjaskldjaslkdjaslkdjaslkdjasdlakjewklajsdlkajsdklasjdlkasdjaklsdjkasljdlkasjdlkajwelkajsldkjaslkdjaslkdjaslkdjalskdjaklsdjalksdjakljeklwajklasjdklasjdklasdjklasjdaklwjeklajsdlkasjdklajskldjasdasdasd";
    private GiftCertificateRepositoryImpl repository;
    private EmbeddedDatabase db;
    private PageFilter page;

    @BeforeEach
    void setup() throws IOException {
        page = new PageFilter();
        db = TestRepository.embeddedDatabase();
        repository = new GiftCertificateRepositoryImpl(TestRepository.sessionFactory(db));
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
                    .description("This is a gift card for Amazon products")
                    .build();
            List<GiftCertificate> result = repository.findGifts(gift);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findGiftsByGiftShouldReturnEmptyList() {
            GiftCertificate certificate = aGift().setCreateDate(null)
                    .setUpdateDate(null)
                    .buildToEntity();
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
package ru.clevertec.ecl.spring.repository.impl;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;
import ru.clevertec.ecl.spring.repository.rowmapper.GiftTagRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class GiftTagRepositoryImplTest {
    private GiftTagRepository repository;
    private EmbeddedDatabase db;
    @BeforeEach
    void setup() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/migration/dev/V1.0.0__InitDB.sql")
                .addScript("classpath:db/migration/dev/V1.0.1__InitGifts.sql")
                .addScript("classpath:db/migration/dev/V1.0.2__InitTags.sql")
                .addScript("classpath:db/migration/dev/V1.0.3__InitGiftTag.sql")
                .build();
        repository = new GiftTagRepositoryImpl(new JdbcTemplate(db.getConnection()), new GiftTagRowMapper(), db);
    }

    @AfterEach
    void teardown() {
        db.shutdown();
    }
    @Nested
    class Save {
        @Test
        void saveAllShouldSaveListOfRelation() {
            List<GiftTag> relations = List.of(
                    GiftTag.builder()
                            .giftID(1)
                            .tagID(3)
                            .build(),
                    GiftTag.builder()
                            .giftID(1)
                            .tagID(4)
                            .build()
            );
            repository.saveAll(relations);
            List<GiftTag> result = repository.findByGift(1).stream()
                    .filter(giftTag -> giftTag.getTagID() >= 3 && giftTag.getTagID() <= 4)
                    .toList();
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void saveShouldSaveRelation() {
            GiftTag relation = GiftTag.builder()
                    .giftID(1)
                    .tagID(5)
                    .build();
            repository.save(relation);
            Optional<GiftTag> result = repository.findByGift(1).stream()
                    .filter(giftTag -> giftTag.getTagID() == 5)
                    .findFirst();
            Assertions.assertThat(result)
                    .isPresent();
        }
        @Test
        void saveShouldThrowSQLException() {
            GiftTag relation = GiftTag.builder()
                    .giftID(1)
                    .tagID(1)
                    .build();
            Assertions.assertThatThrownBy(() -> repository.save(relation))
                    .isInstanceOf(DuplicateKeyException.class);
        }
    }
    @Nested
    class FindBy {
        @Test
        void findByTagShouldNotEmptyList() {
            List<GiftTag> result = repository.findByTag(1);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findByTagShouldEmptyList() {
            List<GiftTag> result = repository.findByTag(100);
            Assertions.assertThat(result)
                    .isEmpty();
        }
        @Test
        void findByGiftShouldNotEmptyList() {
            List<GiftTag> result = repository.findByGift(1);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findByGiftShouldEmpty() {
            List<GiftTag> result = repository.findByGift(100);
            Assertions.assertThat(result)
                    .isEmpty();
        }
    }
}
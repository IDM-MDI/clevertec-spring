package ru.clevertec.ecl.spring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder;
import ru.clevertec.ecl.spring.builder.impl.TagBuilder;
import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:db/migration/test/V1.0.0__InitDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/V1.0.1__InitGifts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/V1.0.2__InitTags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/V1.0.3__InitGiftTag.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/delete-ddl.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository repository;
    private List<GiftCertificate> expected;

    @BeforeEach
    void beforeEach() {
        expected = List.of(
                GiftCertificateBuilder.aGift()
                        .setName("Amazon Gift Card")
                        .setDescription("This is a gift card for Amazon products")
                        .setPrice(new BigDecimal("50"))
                        .setDuration(365)
                        .setCreateDate(LocalDateTime.of(2023,3,24,18,30))
                        .setUpdateDate(LocalDateTime.of(2023,3,24,18,30))
                        .buildToEntity(),
                GiftCertificateBuilder.aGift()
                        .setId(4)
                        .setName("Airbnb Gift Card")
                        .setDescription("This is a gift card for booking stays on Airbnb")
                        .setPrice(new BigDecimal("75"))
                        .setDuration(365)
                        .setCreateDate(LocalDateTime.of(2023,3,22,18,0))
                        .setUpdateDate(LocalDateTime.of(2023,3,22,18,0))
                        .buildToEntity(),
                GiftCertificateBuilder.aGift()
                        .setId(5)
                        .setName("Gourmet Food Gift Basket")
                        .setDescription("This is a gift basket filled with gourmet food items")
                        .setPrice(new BigDecimal("150"))
                        .setDuration(180)
                        .setCreateDate(LocalDateTime.of(2023,3,21,15,0))
                        .setUpdateDate(LocalDateTime.of(2023,3,21,15,0))
                        .buildToEntity()
        );
    }

    @Test
    void findByTagsContainingShouldReturnCorrect() {
        List<GiftCertificate> actual = repository.findByTagsContaining(
                TagBuilder.aTag()
                        .setName("Entertainment")
                        .buildToEntity()
        );
        Assertions.assertThat(actual)
                        .isEqualTo(expected);
    }
    @Test
    void findByTagsContainingShouldReturnEmpty() {
        List<GiftCertificate> actual = repository.findByTagsContaining(
                TagBuilder.aTag()
                        .setId(100)
                        .buildToEntity()
        );
        Assertions.assertThat(actual)
                .isEmpty();
    }
}
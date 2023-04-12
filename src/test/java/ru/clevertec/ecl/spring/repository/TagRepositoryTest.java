package ru.clevertec.ecl.spring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.ecl.spring.entity.Tag;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:db/migration/test/V1.0.0__InitDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/V1.0.2__InitTags.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/migration/test/delete-ddl.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TagRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
    void findByNameLikeIgnoreCaseShouldPresent() {
        Optional<Tag> actual = repository.findByNameLikeIgnoreCase("Entertainment");
        Assertions.assertThat(actual)
                .isPresent();
    }
    @Test
    void findByNameLikeIgnoreCaseShouldEmpty() {
        Optional<Tag> actual = repository.findByNameLikeIgnoreCase("Not Present");
        Assertions.assertThat(actual)
                .isNotPresent();
    }
}
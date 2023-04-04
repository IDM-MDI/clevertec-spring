package ru.clevertec.ecl.spring.repository.impl;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.config.TestRepository;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.RepositoryException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.repository.TagRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;
import static ru.clevertec.ecl.spring.entity.StatusName.DELETED;

@Transactional
class TagRepositoryImplTest {
    private TagRepository repository;
    private EmbeddedDatabase db;
    private PageFilter page;

    @BeforeEach
    void setup() throws IOException {
        db = TestRepository.embeddedDatabase();
        repository = new TagRepositoryImpl(TestRepository.sessionFactory(db));
        page = new PageFilter();
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
            Tag tag = aTag()
                    .setName("Entertainment")
                    .buildToEntity();
            List<Tag> result = repository.findTags(tag);
            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findTagsByTagShouldReturnEmptyList() {
            Tag tag = aTag()
                    .setId(100)
                    .buildToEntity();
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
                    aTag()
                            .setName(exceptionName)
                            .buildToEntity()
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
            Tag expected = aTag().buildToEntity();
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
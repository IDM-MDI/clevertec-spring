package ru.clevertec.ecl.spring.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;
    @InjectMocks
    private TagServiceImpl service;
    private List<Tag> entities;
    private List<TagDTO> models;
    @BeforeEach
    void setup() {
        entities = List.of(
                Tag.builder()
                        .id(1L)
                        .name("tag1")
                        .status(ACTIVE)
                        .build(),
                Tag.builder()
                        .id(2L)
                        .name("tag2")
                        .status(ACTIVE)
                        .build(),
                Tag.builder()
                        .id(3L)
                        .name("tag3")
                        .status(ACTIVE)
                        .build()
        );
        models = List.of(
                TagDTO.builder()
                        .id(1L)
                        .name("tag1")
                        .status(ACTIVE)
                        .build(),
                TagDTO.builder()
                        .id(2L)
                        .name("tag2")
                        .status(ACTIVE)
                        .build(),
                TagDTO.builder()
                        .id(3L)
                        .name("tag3")
                        .status(ACTIVE)
                        .build()
        );
    }
    @Nested
    class FindTag {
        @Test
        void findTagsByPageShouldReturnModelList() {
            doReturn(entities)
                    .when(repository)
                    .findTags(PAGE,SIZE,FILTER,DIRECTION);
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(Tag.class));


            List<TagDTO> result = service.findTags(PAGE, SIZE, FILTER, DIRECTION);

            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findTagsByTagShouldReturnModelList() {
            doReturn(entities.get(0))
                    .when(mapper)
                    .toEntity(any(TagDTO.class));
            doReturn(entities)
                    .when(repository)
                    .findTags(entities.get(0));
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(Tag.class));

            List<TagDTO> result = service.findTags(models.get(0));

            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findTagByIDShouldReturnModelList() {
            long id = 1;
            doReturn(Optional.of(entities.get(0)))
                    .when(repository)
                    .findTag(id);
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(Tag.class));

            TagDTO result = service.findTag(id);

            Assertions.assertThat(result)
                    .isEqualTo(models.get(0));
        }
    }

    @Test
    void saveShouldReturnModel() {
        doReturn(entities.get(0))
                .when(mapper)
                .toEntity(any(TagDTO.class));
        doReturn(entities.get(0))
                .when(repository)
                .save(entities.get(0));
        doReturn(models.get(0))
                .when(mapper)
                .toModel(any(Tag.class));

        TagDTO result = service.save(models.get(0));

        Assertions.assertThat(result)
                .isEqualTo(models.get(0));
    }

    @Test
    void updateShouldReturnModel() {
        long id = 1;
        doReturn(entities.get(0))
                .when(mapper)
                .toEntity(any(TagDTO.class));
        doReturn(entities.get(0))
                .when(repository)
                .update(entities.get(0), id);
        doReturn(models.get(0))
                .when(mapper)
                .toModel(any(Tag.class));

        TagDTO result = service.update(models.get(0), id);

        Assertions.assertThat(result)
                .isEqualTo(models.get(0));
    }

    @Test
    void saveAllShouldReturnModelList() {
        doReturn(entities.get(0))
                .when(mapper)
                .toEntity(any(TagDTO.class));
        doReturn(entities.get(0))
                .when(repository)
                .save(any(Tag.class));
        doReturn(models.get(0))
                .when(mapper)
                .toModel(any(Tag.class));

        List<TagDTO> result = service.saveAll(models);

        Assertions.assertThat(result)
                .isNotEmpty();
    }

    @Test
    void deleteShouldDeleteModel() {
        long id = 1;
        doNothing()
                .when(repository)
                .delete(id);

        service.delete(id);

        verify(repository)
                .delete(id);
    }
}
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
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;
    @InjectMocks
    private TagServiceImpl service;
    private List<Tag> entities;
    private List<TagDTO> models;
    private PageFilter page;
    @BeforeEach
    void setup() {
        page = new PageFilter();
        entities = List.of(
                aTag().buildToEntity(),
                aTag().setId(2).buildToEntity(),
                aTag().setId(3).buildToEntity()
        );
        models = List.of(
                aTag().buildToModel(),
                aTag().setId(2).buildToModel(),
                aTag().setId(3).buildToModel()
        );
    }

    @Nested
    class FindTag {
        @Test
        void findTagsByPageShouldReturnModelList() {
            doReturn(entities)
                    .when(repository)
                    .findTags(page);
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(Tag.class));


            List<TagDTO> result = service.findTags(page);

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
        @Test
        void findTagShouldReturnTag() {
            doReturn(entities.get(0))
                    .when(mapper)
                    .toEntity(any(TagDTO.class));
            doReturn(entities)
                    .when(repository)
                    .findTags(entities.get(0));
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(Tag.class));

            TagDTO result = service.findTag(models.get(0));

            Assertions.assertThat(result)
                    .isEqualTo(models.get(0));
        }
        @Test
        void findTagShouldThrowServiceException() {
            doReturn(entities.get(0))
                    .when(mapper)
                    .toEntity(any(TagDTO.class));
            doReturn(Collections.emptyList())
                    .when(repository)
                    .findTags(entities.get(0));

            Assertions.assertThatThrownBy(() -> service.findTag(models.get(0)))
                    .isInstanceOf(ServiceException.class);
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
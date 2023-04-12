package ru.clevertec.ecl.spring.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.spring.entity.Status;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.util.TagMapper;
import ru.clevertec.ecl.spring.util.TagMapperImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;
import static ru.clevertec.ecl.spring.constant.ExampleMatcherConstant.ENTITY_SEARCH_MATCHER;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;
    private TagMapper realMapper;
    @InjectMocks
    private TagServiceImpl service;
    private List<Tag> entities;
    private List<TagDTO> models;
    @BeforeEach
    void setup() {
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
        realMapper = new TagMapperImpl();
    }

    @Nested
    class FindTag {
        @Test
        void findByPageShouldReturnModelList() {
            doReturn(new PageImpl<>(entities))
                    .when(repository)
                    .findAll(Pageable.ofSize(5));
            entities.forEach(gift -> doReturn(realMapper.toTagDTO(gift)).when(mapper).toTagDTO(gift));

            List<TagDTO> result = service.findAll(Pageable.ofSize(5))
                    .stream()
                    .toList();

            Assertions.assertThat(result)
                    .isEqualTo(models);
        }

        @Test
        void findByTagShouldReturnModelList() {
            Tag tag = entities.get(0);
            TagDTO tagDTO = models.get(0);
            doReturn(tag)
                    .when(mapper)
                    .toTag(tagDTO);
            doReturn(entities)
                    .when(repository)
                    .findAll(Example.of(tag, ENTITY_SEARCH_MATCHER));
            entities.forEach(gift -> doReturn(realMapper.toTagDTO(gift)).when(mapper).toTagDTO(gift));

            List<TagDTO> result = service.findAll(tagDTO);

            Assertions.assertThat(result)
                    .isEqualTo(models);
        }

        @Test
        void findByIDShouldReturnModel() {
            Tag tag = entities.get(0);
            TagDTO tagDTO = models.get(0);
            long id = 1;
            doReturn(Optional.of(tag))
                    .when(repository)
                    .findById(id);
            doReturn(tagDTO)
                    .when(mapper)
                    .toTagDTO(tag);

            TagDTO result = service.findBy(id);

            Assertions.assertThat(result)
                    .isEqualTo(tagDTO);
        }
        @Test
        void findByIDShouldThrowServiceException() {
            long id = 1;
            doReturn(Optional.empty())
                    .when(repository)
                    .findById(id);
            Assertions.assertThatThrownBy(() -> service.findBy(id))
                    .isInstanceOf(ServiceException.class);
        }
        @Test
        void findByNameShouldReturnEntity() {
            String name = "tag";
            Tag tag = entities.get(0);
            doReturn(Optional.of(tag))
                    .when(repository)
                    .findByNameLikeIgnoreCase(name);

            Tag actual = service.findBy(name);

            Assertions.assertThat(actual)
                    .isEqualTo(entities.get(0));
        }
        @Test
        void findByNameShouldThrowServiceException() {
            String name = "tag";

            doReturn(Optional.empty())
                    .when(repository)
                    .findByNameLikeIgnoreCase(name);

            Assertions.assertThatThrownBy(() -> service.findBy(name))
                    .isInstanceOf(ServiceException.class);
        }
    }

    @Test
    void saveShouldReturnModel() {
        doReturn(entities.get(0))
                .when(mapper)
                .toTag(any(TagDTO.class));
        doReturn(entities.get(0))
                .when(repository)
                .save(entities.get(0));
        doReturn(models.get(0))
                .when(mapper)
                .toTagDTO(any(Tag.class));

        TagDTO result = service.save(models.get(0));

        Assertions.assertThat(result)
                .isEqualTo(models.get(0));
    }

    @Test
    void updateShouldReturnModel() {
        Tag tag = entities.get(0);
        TagDTO tagDTO = models.get(0);
        long id = 1;

        doReturn(Optional.of(tag))
                .when(repository)
                .findById(id);
        doReturn(tag)
                .when(mapper)
                .toTag(tagDTO);
        doReturn(tagDTO)
                .when(mapper)
                .toTagDTO(tag);
        doReturn(tag)
                .when(repository)
                .save(tag);

        TagDTO result = service.update(tagDTO, id);

        Assertions.assertThat(result)
                .isEqualTo(tagDTO);
    }

    @Test
    void deleteShouldDeleteModel() {
        Tag tag = entities.get(0);
        tag.setStatus(Status.DELETED);
        TagDTO tagDTO = models.get(0);
        tagDTO.setStatus(Status.DELETED);
        long id = 1;

        doReturn(Optional.of(tag))
                .when(repository)
                .findById(id);
        doReturn(tag)
                .when(mapper)
                .toTag(tagDTO);
        doReturn(tagDTO)
                .when(mapper)
                .toTagDTO(tag);
        doReturn(tag)
                .when(repository)
                .save(tag);

        service.delete(id);

        verify(repository,times(2)).findById(id);
    }
}
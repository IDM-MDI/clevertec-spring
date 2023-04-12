package ru.clevertec.ecl.spring.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Status;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.GiftCertificateMapper;
import ru.clevertec.ecl.spring.util.GiftCertificateMapperImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder.aGift;
import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;
import static ru.clevertec.ecl.spring.constant.ExampleMatcherConstant.ENTITY_SEARCH_MATCHER;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private GiftCertificateMapper mapper;
    private GiftCertificateMapper realMapper;
    @Mock
    private TagService tagService;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    private List<GiftCertificate> entities;
    private List<GiftCertificateDTO> models;
    private List<Tag> tags;
    @BeforeEach
    void setup() {
        realMapper = new GiftCertificateMapperImpl();
        entities = List.of(
                aGift().buildToEntity(),
                aGift().setId(2).buildToEntity(),
                aGift().setId(3).buildToEntity()
        );
        models = List.of(
                aGift().buildToModel(),
                aGift().setId(2).buildToModel(),
                aGift().setId(3).buildToModel()
        );
        tags = List.of(
                aTag().buildToEntity(),
                aTag().setId(2).buildToEntity(),
                aTag().setId(3).buildToEntity()
        );
    }
    @Nested
    class FindGift {
        @Test
        void findAllByPageShouldReturnModelList() {
            doReturn(new PageImpl<>(entities))
                    .when(repository)
                    .findAll(Pageable.ofSize(5));
            entities.forEach(gift -> doReturn(realMapper.toModel(gift)).when(mapper).toModel(gift));

            List<GiftCertificateDTO> result = service.findAll(Pageable.ofSize(5))
                    .stream()
                    .toList();

            Assertions.assertThat(result)
                    .isEqualTo(models);
        }

        @Test
        void findByGiftAndTagShouldReturnModelList() {
            GiftCertificate certificate = entities.get(0);
            GiftCertificateDTO certificateDTO = models.get(0);
            Tag tagEntity = tags.get(0);
            String tag = "tag";

            doReturn(certificate)
                    .when(mapper)
                    .toEntity(certificateDTO);
            doReturn(entities)
                    .when(repository)
                    .findAll(Example.of(certificate,ENTITY_SEARCH_MATCHER));
            doReturn(tagEntity)
                    .when(tagService)
                    .findBy(tag);
            doReturn(entities)
                    .when(repository)
                    .findByTagsContaining(tagEntity);
            entities.forEach(gift -> doReturn(realMapper.toModel(gift)).when(mapper).toModel(gift));

            List<GiftCertificateDTO> result = service.findAll(certificateDTO, tag);
                                            
            Assertions.assertThat(result)
                    .isEqualTo(models);
        }

        @Test
        void findByGiftShouldReturnModelList() {
            GiftCertificate certificate = entities.get(0);
            GiftCertificateDTO certificateDTO = models.get(0);

            doReturn(certificate)
                    .when(mapper)
                    .toEntity(certificateDTO);
            doReturn(entities)
                    .when(repository)
                    .findAll(Example.of(certificate,ENTITY_SEARCH_MATCHER));
            entities.forEach(gift -> doReturn(realMapper.toModel(gift)).when(mapper).toModel(gift));

            List<GiftCertificateDTO> result = service.findAll(certificateDTO, null);

            Assertions.assertThat(result)
                    .isEqualTo(models);
        }
        @Test
        void findByTagShouldReturnModelList() {
            GiftCertificate certificate = entities.get(0);
            GiftCertificateDTO certificateDTO = models.get(0);
            Tag tagEntity = tags.get(0);
            String tag = "tag";

            doReturn(certificate)
                    .when(mapper)
                    .toEntity(certificateDTO);
            doReturn(List.of())
                    .when(repository)
                    .findAll(Example.of(certificate,ENTITY_SEARCH_MATCHER));
            doReturn(tagEntity)
                    .when(tagService)
                    .findBy(tag);
            doReturn(entities)
                    .when(repository)
                    .findByTagsContaining(tagEntity);
            entities.forEach(gift -> doReturn(realMapper.toModel(gift)).when(mapper).toModel(gift));

            List<GiftCertificateDTO> result = service.findAll(certificateDTO, tag);

            Assertions.assertThat(result)
                    .isEqualTo(models);
        }
        @Test
        void findByIDShouldReturnModel() {
            doReturn(Optional.of(entities.get(0)))
                    .when(repository)
                    .findById(1L);
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(GiftCertificate.class));

            GiftCertificateDTO result = service.findBy(1L);

            Assertions.assertThat(result)
                    .isEqualTo(models.get(0));
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
    }

    @Test
    void saveShouldReturnModel() {
        doReturn(entities.get(0))
                .when(mapper)
                .toEntity(models.get(0));
        doReturn(entities.get(0))
                .when(repository)
                .save(entities.get(0));
        doReturn(models.get(0))
                .when(mapper)
                .toModel(entities.get(0));

        GiftCertificateDTO result = service.save(models.get(0));

        Assertions.assertThat(result)
                .isEqualTo(models.get(0));
    }

    @Test
    void updateShouldReturnModel() {
        GiftCertificateDTO certificateDTO = models.get(0);
        GiftCertificate certificate = entities.get(0);

        doReturn(Optional.of(certificate))
                .when(repository)
                .findById(1L);
        doReturn(certificateDTO)
                .when(mapper)
                .toModel(any(GiftCertificate.class));
        doReturn(certificate)
                .when(mapper)
                .toEntity(certificateDTO);
        doReturn(certificate)
                .when(repository)
                .save(certificate);


        GiftCertificateDTO result = service.update(certificateDTO, certificate.getId());

        Assertions.assertThat(result)
                .isEqualTo(certificateDTO);
    }

    @Test
    void deleteShouldDeleteModel() {
        GiftCertificateDTO certificateDTO = GiftCertificateBuilder.aGift().setStatus(Status.DELETED).buildToModel();
        GiftCertificate certificate = GiftCertificateBuilder.aGift().setStatus(Status.DELETED).buildToEntity();
        long id = 1L;

        doReturn(Optional.of(certificate))
                .when(repository)
                .findById(id);
        doReturn(certificateDTO)
                .when(mapper)
                .toModel(any(GiftCertificate.class));
        doReturn(certificate)
                .when(mapper)
                .toEntity(certificateDTO);
        doReturn(certificate)
                .when(repository)
                .save(certificate);

        service.delete(id);

        Mockito.verify(repository, times(2))
                .findById(id);
    }
}
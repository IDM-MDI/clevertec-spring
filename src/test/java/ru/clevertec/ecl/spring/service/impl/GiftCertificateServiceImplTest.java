package ru.clevertec.ecl.spring.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.GiftCertificateMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.spring.builder.impl.GiftCertificateBuilder.aGift;
import static ru.clevertec.ecl.spring.builder.impl.GiftTagBuilder.aGiftTag;
import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private GiftCertificateMapper mapper;
    @Mock
    private TagService tagService;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    private PageFilter page;
    private List<GiftCertificate> entities;
    private List<GiftCertificateDTO> models;
    private List<TagDTO> tags;
    private List<GiftTag> relations;
    @BeforeEach
    void setup() {
        page = new PageFilter();
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
                aTag().buildToModel(),
                aTag().setId(2).buildToModel(),
                aTag().setId(3).buildToModel()
        );
        relations = List.of(
                aGiftTag().buildToEntity(),
                aGiftTag().setTagID(2).buildToEntity(),
                aGiftTag().setTagID(3).buildToEntity()
        );
    }
    @Nested
    class FindGift {
        @Test
        void findGiftsByPageShouldReturnModelList() {
            doReturn(entities)
                    .when(repository)
                    .findGifts(page);
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(GiftCertificate.class));
            doReturn(relations)
                    .when(giftTagService)
                    .findByGift(anyLong());
            doReturn(tags.get(0))
                    .when(tagService)
                    .findTag(anyLong());

            List<GiftCertificateDTO> result = service.findGifts(page);

            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findGiftsByGiftAndTagShouldReturnModelList() {
            doReturn(entities.get(0))
                    .when(mapper)
                    .toEntity(models.get(0));
            doReturn(entities)
                    .when(repository)
                    .findGifts(entities.get(0));
            doReturn(tags.get(0))
                    .when(tagService)
                    .findTag(any(TagDTO.class));
            doReturn(relations)
                    .when(giftTagService)
                    .findByTag(anyLong());
            doReturn(Optional.of(entities.get(0)))
                    .when(repository)
                    .findGift(anyLong());
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(GiftCertificate.class));
            doReturn(relations)
                    .when(giftTagService)
                    .findByGift(anyLong());
            doReturn(tags.get(0))
                    .when(tagService)
                    .findTag(anyLong());

            List<GiftCertificateDTO> result = service.findGifts(models.get(0), "test tag");

            Assertions.assertThat(result)
                    .isNotEmpty();
        }

        @Test
        void findGiftsByGiftShouldReturnModelList() {
            doReturn(entities.get(0))
                    .when(mapper)
                    .toEntity(models.get(0));
            doReturn(entities)
                    .when(repository)
                    .findGifts(entities.get(0));
            doReturn(Optional.of(entities.get(0)))
                    .when(repository)
                    .findGift(anyLong());
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(GiftCertificate.class));
            doReturn(relations)
                    .when(giftTagService)
                    .findByGift(anyLong());
            doReturn(tags.get(0))
                    .when(tagService)
                    .findTag(anyLong());

            List<GiftCertificateDTO> result = service.findGifts(models.get(0), null);

            Assertions.assertThat(result)
                    .isNotEmpty();
        }
        @Test
        void findGiftByIDShouldReturnModel() {
            doReturn(Optional.of(entities.get(0)))
                    .when(repository)
                    .findGift(anyLong());
            doReturn(models.get(0))
                    .when(mapper)
                    .toModel(any(GiftCertificate.class));
            doReturn(tags.get(0))
                    .when(tagService)
                    .findTag(anyLong());
            doReturn(relations)
                    .when(giftTagService)
                    .findByGift(anyLong());

            GiftCertificateDTO result = service.findGift(1L);

            Assertions.assertThat(result)
                    .isNotNull();
        }
        @Test
        void findGiftByIDShouldThrowServiceException() {
            long id = 1;
            doReturn(Optional.empty())
                    .when(repository)
                    .findGift(id);
            Assertions.assertThatThrownBy(() -> service.findGift(id))
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
        doReturn(tags)
                .when(tagService)
                .saveAll(any());
        doNothing()
                .when(giftTagService)
                .save(anyLong(),anyLong());

        GiftCertificateDTO result = service.save(models.get(0));

        Assertions.assertThat(result)
                .isNotNull();
    }

    @Test
    void updateShouldReturnModel() {
        doReturn(entities.get(0))
                .when(mapper)
                .toEntity(models.get(0));
        doReturn(entities.get(0))
                .when(repository)
                .update(entities.get(0),entities.get(0).getId());
        doReturn(models.get(0))
                .when(mapper)
                .toModel(entities.get(0));
        doReturn(tags)
                .when(tagService)
                .saveAll(any());
        doNothing()
                .when(giftTagService)
                .save(anyLong(),anyLong());

        GiftCertificateDTO result = service.update(models.get(0), entities.get(0).getId());

        Assertions.assertThat(result)
                .isNotNull();
    }

    @Test
    void deleteShouldDeleteModel() {
        long id = 1;
        doReturn(Optional.of(entities.get(0)))
                .when(repository)
                .findGift(anyLong());
        doReturn(models.get(0))
                .when(mapper)
                .toModel(any(GiftCertificate.class));
        doReturn(tags.get(0))
                .when(tagService)
                .findTag(anyLong());
        doReturn(relations)
                .when(giftTagService)
                .findByGift(anyLong());
        doNothing()
                .when(repository)
                .delete(id);

        service.delete(id);

        verify(repository)
                .delete(id);
    }
}
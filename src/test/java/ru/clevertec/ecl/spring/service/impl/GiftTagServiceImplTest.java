package ru.clevertec.ecl.spring.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftTagServiceImplTest {
    @Mock
    private GiftTagRepository repository;
    @InjectMocks
    private GiftTagServiceImpl service;

    private List<GiftTag> relations;

    @BeforeEach
    void setup() {
        relations = List.of(
                GiftTag.builder()
                        .giftID(1L)
                        .tagID(1L)
                        .build(),
                GiftTag.builder()
                        .giftID(1L)
                        .tagID(2L)
                        .build(),
                GiftTag.builder()
                        .giftID(1L)
                        .tagID(3L)
                        .build()
        );
    }

    @Test
    void saveAllShouldReturnEntity() {
        doNothing()
                .when(repository)
                .saveAll(relations);

        service.saveAll(relations);

        verify(repository).saveAll(relations);
    }

    @Test
    void saveShouldReturnEntity() {
        doNothing()
                .when(repository)
                .save(relations.get(0));

        service.save(relations.get(0));

        verify(repository).save(relations.get(0));
    }

    @Test
    void findByTagShouldReturnEntity() {
        long id = 1;
        doReturn(relations)
                .when(repository)
                .findByTag(id);

        List<GiftTag> result = service.findByTag(id);

        Assertions.assertThat(result)
                .containsAll(relations);
    }

    @Test
    void findByGiftShouldReturnEntity() {
        long id = 1;
        doReturn(relations)
                .when(repository)
                .findByGift(id);

        List<GiftTag> result = service.findByGift(id);

        Assertions.assertThat(result)
                .containsAll(relations);
    }
}
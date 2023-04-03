package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;
import ru.clevertec.ecl.spring.service.GiftTagService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftTagServiceImpl implements GiftTagService {
    private final GiftTagRepository repository;
    @Override
    public void saveAll(List<GiftTag> relations) {
        repository.saveAll(relations);
    }

    @Override
    public void save(GiftTag relation) {
        repository.save(relation);
    }

    @Override
    public void save(long gift, long tag) {
        save(GiftTag.builder()
                .giftID(gift)
                .tagID(tag)
                .build()
        );
    }

    @Override
    public List<GiftTag> findByTag(long id) {
        return repository.findByTag(id);
    }

    @Override
    public List<GiftTag> findByGift(long id) {
        return repository.findByGift(id);
    }
}

package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.service.GiftTagService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GIftTagServiceImpl implements GiftTagService {
    @Override
    public boolean saveAll(List<GiftTag> relations) {
        return false;
    }

    @Override
    public boolean save(GiftTag relation) {
        return false;
    }

    @Override
    public List<GiftTag> findByTag(long id) {
        return null;
    }

    @Override
    public List<GiftTag> findByGift(long id) {
        return null;
    }
}

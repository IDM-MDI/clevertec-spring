package ru.clevertec.ecl.spring.service;


import ru.clevertec.ecl.spring.entity.GiftTag;

import java.util.List;

public interface GiftTagService {
    void saveAll(List<GiftTag> relations);
    void save(GiftTag relation);
    void save(long gift, long tag);
    List<GiftTag> findByTag(long id);
    List<GiftTag> findByGift(long id);
}

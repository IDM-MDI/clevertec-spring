package ru.clevertec.ecl.spring.service;


import ru.clevertec.ecl.spring.entity.GiftTag;

import java.util.List;

public interface GiftTagService {
    boolean saveAll(List<GiftTag> relations);
    boolean save(GiftTag relation);
    List<GiftTag> findByTag(long id);
    List<GiftTag> findByGift(long id);
}

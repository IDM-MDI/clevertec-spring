package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftTag;

import java.util.List;

public interface GiftTagRepository {
    boolean saveAll(List<GiftTag> relations);
    boolean save(GiftTag relation);
    List<GiftTag> findByTag(long id);
    List<GiftTag> findByGift(long id);
}

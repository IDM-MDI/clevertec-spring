package ru.clevertec.ecl.spring.service;


import ru.clevertec.ecl.spring.entity.GiftTag;

import java.sql.SQLException;
import java.util.List;

public interface GiftTagService {
    void saveAll(List<GiftTag> relations);
    void save(GiftTag relation);
    List<GiftTag> findByTag(long id) throws SQLException;
    List<GiftTag> findByGift(long id) throws SQLException;
}

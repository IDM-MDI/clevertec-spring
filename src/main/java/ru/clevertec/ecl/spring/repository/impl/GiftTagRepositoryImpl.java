package ru.clevertec.ecl.spring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftTagRepositoryImpl implements GiftTagRepository {
    private final JdbcTemplate template;
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

package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.repository.GiftTagRepository;
import ru.clevertec.ecl.spring.service.GiftTagService;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftTagServiceImpl implements GiftTagService {
    private final GiftTagRepository repository;
    @Override
    public void saveAll(List<GiftTag> relations) {
        relations.forEach(repository::save);
    }

    @Override
    public void save(GiftTag relation) {
        repository.save(relation);
    }

    @Override
    public List<GiftTag> findByTag(long id) throws SQLException {
        return repository.findByTag(id);
    }

    @Override
    public List<GiftTag> findByGift(long id) throws SQLException {
        return repository.findByGift(id);
    }
}

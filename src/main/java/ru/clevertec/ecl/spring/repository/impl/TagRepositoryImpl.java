package ru.clevertec.ecl.spring.repository.impl;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.repository.TagRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate template;
    @Override
    public List<GiftCertificate> findTags(int page, int size, String filter, String direction) {
        return null;
    }

    @Override
    public List<GiftCertificate> findTags(Tag tag) {
        return null;
    }

    @Override
    public GiftCertificate findTag(long id) {
        return null;
    }

    @Override
    public GiftCertificate save(Tag tag) {
        return null;
    }

    @Override
    public GiftCertificate update(Tag tag) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}

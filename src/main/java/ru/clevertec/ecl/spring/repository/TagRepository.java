package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Tag;

import java.util.List;

public interface TagRepository {
    List<GiftCertificate> findTags(int page, int size, String filter, String direction);
    List<GiftCertificate> findTags(Tag tag);
    GiftCertificate findTag(long id);
    GiftCertificate save(Tag tag);
    GiftCertificate update(Tag tag);
    boolean delete(long id);
}

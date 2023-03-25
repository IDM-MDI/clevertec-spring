package ru.clevertec.ecl.spring.repository.impl;

import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;

import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @Override
    public List<GiftCertificate> findGifts(int page, int size, String filter, String direction) {
        return null;
    }

    @Override
    public List<GiftCertificate> findGifts(GiftCertificate certificate) {
        return null;
    }

    @Override
    public GiftCertificate findGift(long id) {
        return null;
    }

    @Override
    public GiftCertificate save(GiftCertificate certificate) {
        return null;
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}

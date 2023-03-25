package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository {
    List<GiftCertificate> findGifts(int page, int size, String filter, String direction);
    List<GiftCertificate> findGifts(GiftCertificate certificate);
    GiftCertificate findGift(long id);
    GiftCertificate save(GiftCertificate certificate);
    GiftCertificate update(GiftCertificate certificate);
    boolean delete(long id);
}

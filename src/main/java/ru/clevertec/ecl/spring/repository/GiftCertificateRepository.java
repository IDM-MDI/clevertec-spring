package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findGifts(int page, int size, String filter, String direction);
    List<GiftCertificate> findGifts(GiftCertificate certificate);
    Optional<GiftCertificate> findGift(long id);
    GiftCertificate save(GiftCertificate certificate);
    GiftCertificate update(GiftCertificate certificate, long id);
    void delete(long id);
}

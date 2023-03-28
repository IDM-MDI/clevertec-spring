package ru.clevertec.ecl.spring.repository;

import jakarta.validation.constraints.NotNull;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.model.PageFilter;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findGifts(@NotNull PageFilter page);
    List<GiftCertificate> findGifts(GiftCertificate certificate);
    Optional<GiftCertificate> findGift(long id);
    GiftCertificate save(GiftCertificate certificate);
    GiftCertificate update(GiftCertificate certificate, long id);
    void delete(long id);
}

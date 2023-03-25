package ru.clevertec.ecl.spring.repository;

import ru.clevertec.ecl.spring.entity.GiftCertificate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findGifts(int page, int size, String filter, String direction) throws SQLException;
    List<GiftCertificate> findGifts(GiftCertificate certificate) throws SQLException;
    Optional<GiftCertificate> findGift(long id) throws SQLException;
    GiftCertificate save(GiftCertificate certificate) throws SQLException;
    GiftCertificate update(GiftCertificate certificate, long id) throws SQLException;
    void delete(long id) throws SQLException;
}

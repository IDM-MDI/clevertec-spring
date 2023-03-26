package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.sql.SQLException;
import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDTO> findGifts(int page, int size, String filter, String direction) throws SQLException;
    List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) throws SQLException;
    GiftCertificateDTO findGift(long id) throws SQLException;
    GiftCertificateDTO save(GiftCertificateDTO gift) throws SQLException;
    GiftCertificateDTO update(GiftCertificateDTO gift,long id) throws SQLException;

    void delete(long id) throws SQLException;
}

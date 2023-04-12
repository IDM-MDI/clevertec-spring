package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDTO> findGifts(PageFilter page);
    List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag);
    GiftCertificateDTO findGift(long id);
    GiftCertificateDTO save(GiftCertificateDTO gift);
    GiftCertificateDTO update(GiftCertificateDTO gift,long id);

    void delete(long id);
}

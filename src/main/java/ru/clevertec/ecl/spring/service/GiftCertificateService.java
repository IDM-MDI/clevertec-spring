package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDTO> findGifts(int page, int size, String filter, String direction);
    List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag);
    GiftCertificateDTO findGift(long id);
    GiftCertificateDTO save(GiftCertificateDTO gift);
    GiftCertificateDTO update(GiftCertificateDTO gift,long id);
}

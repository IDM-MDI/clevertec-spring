package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDTO> findAll(int page, int size, String filter, String direction);
    GiftCertificateDTO findByID(long id);
    GiftCertificateDTO save(GiftCertificateDTO tag);
    GiftCertificateDTO update(GiftCertificateDTO tag,long id);
}

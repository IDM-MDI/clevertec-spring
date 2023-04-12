package ru.clevertec.ecl.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateService {
    Page<GiftCertificateDTO> findAll(Pageable page);
    List<GiftCertificateDTO> findAll(GiftCertificateDTO gift, String tag);
    GiftCertificateDTO findBy(long id);
    GiftCertificateDTO save(GiftCertificateDTO gift);
    GiftCertificateDTO update(GiftCertificateDTO gift,long id);

    void delete(long id);
}

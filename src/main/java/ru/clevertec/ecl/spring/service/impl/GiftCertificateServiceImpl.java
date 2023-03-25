package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.service.GiftCertificateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Override
    public List<GiftCertificateDTO> findAll(int page, int size, String filter, String direction) {
        return null;
    }

    @Override
    public GiftCertificateDTO findByID(long id) {
        return null;
    }

    @Override
    public GiftCertificateDTO save(GiftCertificateDTO tag) {
        return null;
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO tag, long id) {
        return null;
    }
}

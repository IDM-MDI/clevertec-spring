package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.GiftCertificateService;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.GiftCertificateMapper;

import java.util.List;

import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper mapper;
    private final TagService tagService;

    @Override
    public List<GiftCertificateDTO> findGifts(PageFilter page) {
        return repository.findGifts(page)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) {
        List<GiftCertificate> byGift = repository.findGifts(mapper.toEntity(gift));
        byGift = StringUtils.isNotBlank(tag) ? searchByTag(byGift,tag) : byGift;
        return findByIDAll(byGift);
    }

    @Override
    public GiftCertificateDTO findGift(long id) {
        return repository.findGift(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
    }

    @Override
    public GiftCertificateDTO save(GiftCertificateDTO gift) {
        return mapper.toModel(repository.save(mapper.toEntity(gift)));
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO gift, long id) {
        return mapper.toModel(repository.update(mapper.toEntity(gift), id));
    }

    @Override
    public void delete(long id) {
        findGift(id);
        repository.delete(id);
    }
    private List<GiftCertificateDTO> findByIDAll(List<GiftCertificate> byGift) {
        return byGift.stream()
                .map(giftCertificate -> findGift(giftCertificate.getId()))
                .toList();
    }
    private List<GiftCertificate> searchByTag(List<GiftCertificate> byGift,String tag) {
        TagDTO tagDTO = tagService.findTag(TagDTO.builder().name(tag).build());
        return byGift.stream()
                .filter(foundedGift -> foundedGift.getTags()
                        .stream()
                        .anyMatch(tags -> tags.getName().equals(tagDTO.getName())))
                .toList();
    }
}
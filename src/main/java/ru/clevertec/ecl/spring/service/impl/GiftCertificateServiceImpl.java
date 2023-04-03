package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.GiftCertificateService;
import ru.clevertec.ecl.spring.service.GiftTagService;
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
    private final GiftTagService giftTagService;

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findGifts(PageFilter page) {
        List<GiftCertificateDTO> gifts = repository.findGifts(page)
                .stream()
                .map(mapper::toModel)
                .toList();
        fillGiftsWithTag(gifts);
        return gifts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) {
        List<GiftCertificate> byGift = repository.findGifts(mapper.toEntity(gift));
        byGift = StringUtils.isNotBlank(tag) ? searchByTag(byGift,tag) : byGift;
        return findByIDAll(byGift);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDTO findGift(long id) {
        GiftCertificateDTO gift = repository.findGift(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
        gift.setTags(fillGiftWithTag(id));
        return gift;
    }

    @Override
    @Transactional
    public GiftCertificateDTO save(GiftCertificateDTO gift) {
        GiftCertificateDTO saved = mapper.toModel(repository.save(mapper.toEntity(gift)));
        saveTagRelation(gift, saved);
        return saved;
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO gift, long id) {
        GiftCertificateDTO updated = mapper.toModel(repository.update(mapper.toEntity(gift), id));
        saveTagRelation(gift, updated);
        return updated;
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(long id) {
        findGift(id);
        repository.delete(id);
    }

    private void saveTagRelation(GiftCertificateDTO gift, GiftCertificateDTO saved) {
        saved.setTags(tagService.saveAll(gift.getTags()));
        saved.getTags().forEach(tag -> giftTagService.save(saved.getId(),tag.getId()));
    }

    private void fillGiftsWithTag(List<GiftCertificateDTO> gifts) {
        gifts.forEach(gift -> gift.setTags(fillGiftWithTag(gift.getId())));
    }

    private List<GiftCertificateDTO> findByIDAll(List<GiftCertificate> byGift) {
        return byGift.stream()
                .map(giftCertificate -> findGift(giftCertificate.getId()))
                .toList();
    }
    private List<GiftCertificate> searchByTag(List<GiftCertificate> byGift,String tag) {
        TagDTO tagDTO = tagService.findTag(TagDTO.builder().name(tag).build());
        List<GiftTag> relations = giftTagService.findByTag(tagDTO.getId());
        return byGift.stream()
                .filter(foundedGift -> relations.stream().anyMatch(giftTag -> giftTag.getGiftID() == foundedGift.getId()))
                .toList();
    }
    private List<TagDTO> fillGiftWithTag(long giftID) {
        return giftTagService.findByGift(giftID)
                .stream()
                .map(giftTag -> tagService.findTag(giftTag.getTagID()))
                .toList();
    }
}
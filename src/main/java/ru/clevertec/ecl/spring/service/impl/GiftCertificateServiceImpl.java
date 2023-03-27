package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.exception.ExceptionStatus;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
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
    public List<GiftCertificateDTO> findGifts(int page, int size, String filter, String direction) {
        List<GiftCertificateDTO> gifts = repository.findGifts(page, size, filter, direction)
                .stream()
                .map(mapper::toModel)
                .toList();
        fillGiftsWithTag(gifts);
        return gifts;
    }

    @Override
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) {
        List<GiftCertificate> byGift = repository.findGifts(mapper.toEntity(gift));
        if(StringUtils.isNotBlank(tag)) {
            byGift = searchByTag(byGift,tag);
        }
        return findByIDAll(byGift);
    }

    @Override
    public GiftCertificateDTO findGift(long id) {
        GiftCertificateDTO gift = repository.findGift(id)
                .map(mapper::toModel)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
        gift.setTags(fillGiftWithTag(id));
        return gift;
    }

    @Override
    public GiftCertificateDTO save(GiftCertificateDTO gift) {
        GiftCertificateDTO saved = mapper.toModel(repository.save(mapper.toEntity(gift)));
        saveTagRelation(gift, saved);
        return saved;
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO gift, long id) {
        GiftCertificateDTO updated = mapper.toModel(repository.update(mapper.toEntity(gift), id));
        saveTagRelation(gift, updated);
        return updated;
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    private void saveTagRelation(GiftCertificateDTO gift, GiftCertificateDTO saved) {
        saved.setTags(tagService.saveAll(gift.getTags()));
        saved.getTags()
                .forEach(tag -> giftTagService.save(
                        GiftTag.builder()
                                .giftID(saved.getId())
                                .tagID(tag.getId())
                                .build()
                        )
                );
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
        TagDTO tagDTO = tagService.findTags(
                        TagDTO.builder()
                                .name(tag)
                                .build()
                ).stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
        List<GiftTag> relations = giftTagService.findByTag(tagDTO.getId());
        return byGift.stream()
                .filter(foundedGift ->
                        relations.stream().anyMatch(giftTag -> giftTag.getGiftID() == foundedGift.getId())
                )
                .toList();
    }
    private List<TagDTO> fillGiftWithTag(long giftID) {
        return giftTagService.findByGift(giftID)
                .stream()
                .map(giftTag -> tagService.findTag(giftTag.getTagID()))
                .toList();
    }
}
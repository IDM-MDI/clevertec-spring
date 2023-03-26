package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.GiftTag;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.GiftCertificateService;
import ru.clevertec.ecl.spring.service.GiftTagService;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.GiftCertificateMapper;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper mapper;
    private final TagService tagService;
    private final GiftTagService giftTagService;
    private final TagMapper tagMapper;

    @Override
    public List<GiftCertificateDTO> findGifts(int page, int size, String filter, String direction) throws SQLException {
        List<GiftCertificateDTO> gifts = repository.findGifts(page, size, filter, direction)
                .stream()
                .map(mapper::toModel)
                .toList();
        fillGiftsWithTag(gifts);
        return gifts;
    }

    @Override
    public List<GiftCertificateDTO> findGifts(GiftCertificateDTO gift, String tag) throws SQLException {
        List<GiftCertificate> byGift = repository.findGifts(mapper.toEntity(gift));
        if(StringUtils.isNotBlank(tag)) {
            byGift = searchByTag(byGift);
        }
        return findByIDAll(byGift);
    }

    @Override
    public GiftCertificateDTO findGift(long id) throws SQLException {
        GiftCertificateDTO gift = repository.findGift(id)
                .map(mapper::toModel)
                .orElseThrow();
        gift.setTags(fillGiftWithTag(id));
        return gift;
    }

    @Override
    public GiftCertificateDTO save(GiftCertificateDTO gift) throws SQLException {
        GiftCertificateDTO saved = mapper.toModel(repository.save(mapper.toEntity(gift)));
        saved.setTags(
                tagService.saveAll(gift.getTags())
                        .stream()
                        .map(tagMapper::toModel)
                        .toList()
        );
        saved.getTags().forEach(tag -> GiftTag.builder()
                        .giftID(saved.getId())
                        .tagID(tag.getId())
                        .build());
        return saved;
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO gift, long id) throws SQLException {
        GiftCertificateDTO updated = mapper.toModel(repository.update(mapper.toEntity(gift), id));
        updated.setTags(
                tagService.saveAll(gift.getTags())
                        .stream()
                        .map(tagMapper::toModel)
                        .toList()
        );
        updated.getTags().forEach(tag -> GiftTag.builder()
                .giftID(updated.getId())
                .tagID(tag.getId())
                .build());
        return updated;
    }

    @Override
    public void delete(long id) throws SQLException {
        repository.delete(id);
    }

    private void fillGiftsWithTag(List<GiftCertificateDTO> gifts) throws SQLException {
        for (GiftCertificateDTO gift : gifts) {
            gift.setTags(fillGiftWithTag(gift.getId()));
        }
    }

    private List<GiftCertificateDTO> findByIDAll(List<GiftCertificate> byGift) throws SQLException {
        List<GiftCertificateDTO> result = new ArrayList<>();
        for (GiftCertificate giftCertificate : byGift) {
            result.add(findGift(giftCertificate.getId()));
        }
        return result;
    }
    private List<GiftCertificate> searchByTag(List<GiftCertificate> byGift) throws SQLException {
        TagDTO tagDTO = tagService.findTags(
                        TagDTO.builder()
                                .build()
                ).stream()
                .findFirst()
                .orElseThrow();
        List<GiftTag> relations = giftTagService.findByTag(tagDTO.getId());
        return byGift.stream()
                .filter(foundedGift ->
                        relations.stream().anyMatch(giftTag -> giftTag.getGiftID() == foundedGift.getId())
                )
                .toList();
    }
    private List<TagDTO> fillGiftWithTag(long giftID) throws SQLException {
        List<TagDTO> list = new ArrayList<>();
        for (GiftTag giftTag : giftTagService.findByGift(giftID)) {
            list.add(tagService.findTag(giftTag.getTagID()));
        }
        return list;
    }
}

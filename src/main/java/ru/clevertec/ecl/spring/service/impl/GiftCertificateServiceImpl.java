package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.entity.Status;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.repository.GiftCertificateRepository;
import ru.clevertec.ecl.spring.service.GiftCertificateService;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.GiftCertificateMapper;

import java.util.List;

import static ru.clevertec.ecl.spring.constant.ExampleMatcherConstant.ENTITY_SEARCH_MATCHER;
import static ru.clevertec.ecl.spring.constant.ExceptionStatus.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper mapper;
    private final TagService tagService;

    @Override
    public Page<GiftCertificateDTO> findAll(Pageable page) {
        return repository.findAll(page)
                .map(mapper::toGiftCertificateDTO);
    }

    @Override
    public List<GiftCertificateDTO> findAll(GiftCertificateDTO gift, String tag) {
        List<GiftCertificate> byGift = repository.findAll(Example.of(mapper.toGiftCertificate(gift),ENTITY_SEARCH_MATCHER));
        byGift = StringUtils.isNotBlank(tag) ? searchByTag(byGift,tag) : byGift;
        return byGift
                .stream()
                .map(mapper::toGiftCertificateDTO)
                .toList();
    }

    @Override
    public GiftCertificateDTO findBy(long id) {
        return repository.findById(id)
                .map(mapper::toGiftCertificateDTO)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
    }

    @Override
    @Transactional
    public GiftCertificateDTO save(GiftCertificateDTO gift) {
        return mapper.toGiftCertificateDTO(repository.save(mapper.toGiftCertificate(gift)));
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO gift, long id) {
        GiftCertificateDTO fromDB = findBy(id);
        BeanUtils.copyProperties(gift, fromDB);
        GiftCertificate updatable = mapper.toGiftCertificate(fromDB);
        return mapper.toGiftCertificateDTO(repository.save(updatable));
    }

    @Override
    @Transactional
    public void delete(long id) {
        GiftCertificateDTO gift = findBy(id);
        gift.setStatus(Status.DELETED);
        update(gift, id);
    }

    private List<GiftCertificate> searchByTag(List<GiftCertificate> byGift,String tag) {
        List<GiftCertificate> result = repository.findByTagsContaining(tagService.findBy(tag));
        return byGift.isEmpty() ?
                result
                :
                result.stream()
                        .filter(byTag -> byGift.stream().anyMatch(gift -> gift.equals(byTag)))
                        .toList();
    }
}
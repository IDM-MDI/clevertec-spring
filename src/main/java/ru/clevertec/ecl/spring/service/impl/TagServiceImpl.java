package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.Status;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.util.List;

import static ru.clevertec.ecl.spring.constant.ExampleMatcherConstant.ENTITY_SEARCH_MATCHER;
import static ru.clevertec.ecl.spring.constant.ExceptionStatus.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;
    @Override
    public Page<TagDTO> findAll(Pageable page) {
        return repository.findAll(page)
                .map(mapper::toModel);
    }

    @Override
    public List<TagDTO> findAll(TagDTO tag) {
        return repository.findAll(Example.of(mapper.toEntity(tag), ENTITY_SEARCH_MATCHER))
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public TagDTO findBy(long id) {
        return mapper.toModel(
                repository.findById(id)
                        .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public Tag findBy(String name) {
        return repository.findByNameLikeIgnoreCase(name)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
    }

    @Override
    public TagDTO save(TagDTO tag) {
        return mapper.toModel(repository.save(mapper.toEntity(tag)));
    }

    @Override
    public TagDTO update(TagDTO tag, long id) {
        TagDTO fromDB = findBy(id);
        BeanUtils.copyProperties(tag, fromDB);
        return mapper.toModel(repository.save(mapper.toEntity(fromDB)));
    }

    @Override
    public void delete(long id) {
        TagDTO tag = findBy(id);
        tag.setStatus(Status.DELETED);
        update(tag,id);
    }
}

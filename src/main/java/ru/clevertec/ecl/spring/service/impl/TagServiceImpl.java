package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.exception.ServiceException;
import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.util.List;

import static ru.clevertec.ecl.spring.exception.ExceptionStatus.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;
    @Override
    public List<TagDTO> findTags(PageFilter page) {
        return repository.findTags(page)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<TagDTO> findTags(TagDTO tag) {
        return repository.findTags(mapper.toEntity(tag))
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public TagDTO findTag(long id) {
        return mapper.toModel(
                repository.findTag(id)
                        .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()))
        );
    }

    @Override
    public TagDTO save(TagDTO tag) {
        return mapper.toModel(repository.save(mapper.toEntity(tag)));
    }

    @Override
    public TagDTO update(TagDTO tag, long id) {
        return mapper.toModel(repository.update(mapper.toEntity(tag),id));
    }

    @Override
    public List<TagDTO> saveAll(List<TagDTO> tags) {
        return tags.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}

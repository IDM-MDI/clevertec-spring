package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;
    @Override
    public List<TagDTO> findTags(int page, int size, String filter, String direction) {
        return repository.findTags(page, size, filter, direction)
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
                        .orElseThrow()
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
    public List<Tag> saveAll(List<TagDTO> tags) {
        return tags.stream()
                .map(this::save)
                .map(mapper::toEntity)
                .toList();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}

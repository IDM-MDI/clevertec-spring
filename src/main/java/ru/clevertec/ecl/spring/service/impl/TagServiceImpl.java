package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.repository.TagRepository;
import ru.clevertec.ecl.spring.service.TagService;
import ru.clevertec.ecl.spring.util.TagMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;
    @Override
    public List<TagDTO> findTags(int page, int size, String filter, String direction) throws SQLException {
        return repository.findTags(page, size, filter, direction)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<TagDTO> findTags(TagDTO tag) throws SQLException {
        return repository.findTags(mapper.toEntity(tag))
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public TagDTO findTag(long id) throws SQLException {
        return mapper.toModel(
                repository.findTag(id)
                        .orElseThrow()
        );
    }

    @Override
    public TagDTO save(TagDTO tag) throws SQLException {
        return mapper.toModel(repository.save(mapper.toEntity(tag)));
    }

    @Override
    public TagDTO update(TagDTO tag, long id) throws SQLException {
        return mapper.toModel(repository.update(mapper.toEntity(tag),id));
    }

    @Override
    public List<Tag> saveAll(List<TagDTO> tags) throws SQLException {
        List<TagDTO> list = new ArrayList<>();
        for (TagDTO tag : tags) {
            list.add(save(tag));
        }
        return list.stream().map(mapper::toEntity).toList();
    }

    @Override
    public void delete(long id) throws SQLException {
        repository.delete(id);
    }
}

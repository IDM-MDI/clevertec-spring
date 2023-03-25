package ru.clevertec.ecl.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;
import ru.clevertec.ecl.spring.service.TagService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    @Override
    public List<TagDTO> findAll(int page, int size, String filter, String direction) {
        return null;
    }

    @Override
    public TagDTO findByID(long id) {
        return null;
    }

    @Override
    public TagDTO save(TagDTO tag) {
        return null;
    }

    @Override
    public TagDTO update(TagDTO tag, long id) {
        return null;
    }

    @Override
    public List<Tag> saveAll(List<TagDTO> tags) {
        return null;
    }
}

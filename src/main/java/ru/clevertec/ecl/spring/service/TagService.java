package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findTags(int page, int size, String filter, String direction);
    List<TagDTO> findTags(TagDTO tag);
    TagDTO findTag(long id);
    TagDTO save(TagDTO tag);
    TagDTO update(TagDTO tag,long id);
    List<Tag> saveAll(List<TagDTO> tags);

    void delete(long id);
}

package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.model.PageFilter;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> findTags(PageFilter page);
    List<TagDTO> findTags(TagDTO tag);
    TagDTO findTag(long id);
    TagDTO save(TagDTO tag);
    TagDTO update(TagDTO tag,long id);
    List<TagDTO> saveAll(List<TagDTO> tags);

    void delete(long id);
}

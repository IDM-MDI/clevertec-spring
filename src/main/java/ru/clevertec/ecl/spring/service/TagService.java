package ru.clevertec.ecl.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.util.List;

public interface TagService {
    Page<TagDTO> findAll(Pageable page);
    List<TagDTO> findAll(TagDTO tag);
    TagDTO findBy(TagDTO tag);
    TagDTO findBy(long id);
    Tag findBy(String name);
    TagDTO save(TagDTO tag);
    TagDTO update(TagDTO tag,long id);
    List<TagDTO> saveAll(List<TagDTO> tags);

    void delete(long id);
}

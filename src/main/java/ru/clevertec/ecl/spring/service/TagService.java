package ru.clevertec.ecl.spring.service;

import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.sql.SQLException;
import java.util.List;

public interface TagService {
    List<TagDTO> findTags(int page, int size, String filter, String direction) throws SQLException;
    List<TagDTO> findTags(TagDTO tag) throws SQLException;
    TagDTO findTag(long id) throws SQLException;
    TagDTO save(TagDTO tag) throws SQLException;
    TagDTO update(TagDTO tag,long id) throws SQLException;
    List<Tag> saveAll(List<TagDTO> tags) throws SQLException;
}

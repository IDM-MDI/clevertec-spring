package ru.clevertec.ecl.spring.builder.impl;

import ru.clevertec.ecl.spring.builder.TestEntityBuilder;
import ru.clevertec.ecl.spring.builder.TestModelBuilder;
import ru.clevertec.ecl.spring.entity.Tag;
import ru.clevertec.ecl.spring.model.TagDTO;

import static ru.clevertec.ecl.spring.constant.StatusName.ACTIVE;

public class TagBuilder implements TestEntityBuilder<Tag>, TestModelBuilder<TagDTO> {
    private long id = 1;
    private String name = "test";
    private String status = ACTIVE;
    private TagBuilder() {}
    public static TagBuilder aTag() {
        return new TagBuilder();
    }
    @Override
    public Tag buildToEntity() {
        return Tag.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }

    @Override
    public TagDTO buildToModel() {
        return TagDTO.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }

    public TagBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TagBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TagBuilder setStatus(String status) {
        this.status = status;
        return this;
    }
}

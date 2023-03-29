package ru.clevertec.ecl.spring.builder.impl;

import ru.clevertec.ecl.spring.builder.TestEntityBuilder;
import ru.clevertec.ecl.spring.builder.TestModelBuilder;
import ru.clevertec.ecl.spring.entity.GiftCertificate;
import ru.clevertec.ecl.spring.model.GiftCertificateDTO;
import ru.clevertec.ecl.spring.model.TagDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static ru.clevertec.ecl.spring.builder.impl.TagBuilder.aTag;
import static ru.clevertec.ecl.spring.entity.StatusName.ACTIVE;

public class GiftCertificateBuilder implements TestEntityBuilder<GiftCertificate>, TestModelBuilder<GiftCertificateDTO> {
    private long id = 1;
    private String name = "test";
    private String description = "some test description";
    private BigDecimal price = new BigDecimal(100);
    private long duration = 100;
    private LocalDateTime createDate = LocalDateTime.of(2000, 1, 1, 10 ,10, 10);
    private LocalDateTime updateDate = LocalDateTime.of(2000, 1, 1, 10 ,10, 10);
    private List<TagDTO> tags = List.of(
            aTag().buildToModel(),
            aTag().setId(2).buildToModel(),
            aTag().setId(3).buildToModel()
    );
    private String status = ACTIVE;
    @Override
    public GiftCertificate buildToEntity() {
        return null;
    }

    @Override
    public GiftCertificateDTO buildToModel() {
        return null;
    }

    public GiftCertificateBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public GiftCertificateBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GiftCertificateBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public GiftCertificateBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public GiftCertificateBuilder setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public GiftCertificateBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public GiftCertificateBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public GiftCertificateBuilder setTags(List<TagDTO> tags) {
        this.tags = tags;
        return this;
    }

    public GiftCertificateBuilder setStatus(String status) {
        this.status = status;
        return this;
    }
}
